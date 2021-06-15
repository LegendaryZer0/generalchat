package sb.rf.generalchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.RedisUser;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.UserLoginDto;
import sb.rf.generalchat.repository.RedisUserRepository;
import sb.rf.generalchat.security.util.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ApiAuthServiceImpl implements ApiAuthService {

    @Autowired
    public RedisUserRepository redisUserRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Qualifier("activeUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void processUser(UserLoginDto userLoginDto, HttpServletResponse response, String deviceName) {
        Optional<User> userOptional = userService.getUser(userLoginDto.getLogin(), userLoginDto.getPassword());
        if (userOptional.isPresent()) {
            UUID refreshToken = UUID.randomUUID();
            String accessToken = generateAccessToken(userLoginDto.getLogin());
            Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken.toString());
            Cookie accessTokenCookie = new Cookie("access_token", accessToken);
            refreshTokenCookie.setPath("/api");
            accessTokenCookie.setPath("/api");
            addHttpOnlyCookiesToUser(response, refreshTokenCookie, accessTokenCookie);
            addUserInRedis(userOptional.get(), refreshToken.toString(), deviceName);

            log.info(" refresh token is :: {}", refreshToken);
        } else {
            response.setStatus(403);
        }
    }

    private void addHttpOnlyCookiesToUser(HttpServletResponse response, Cookie... cookies) {
        Arrays.stream(cookies).peek(x -> x.setHttpOnly(true)).forEach(response::addCookie);
    }

    public void addUserInRedis(User user, String refreshToken, String deviceName) {
        log.info("devicename is {} and user id is {}", deviceName, user.getId());

        Optional<RedisUser> redisUser = redisUserRepository.findRedisUserByDeviceNameAndUserId(deviceName, user.getId());
        log.info("founded redis user {}", redisUser);
        redisUser.ifPresent(value -> redisUserRepository.delete(value));

        redisUserRepository.save(RedisUser.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .deviceName(deviceName)
                .build());

    }

    public String generateAccessToken(String email) {
        log.info("email {}",email);
        return jwtUtil.generateToken(userDetailsService.loadUserByUsername(email));
    }

}
