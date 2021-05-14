package sb.rf.generalchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.RedisUser;
import sb.rf.generalchat.repository.RedisUserRepository;
import sb.rf.generalchat.security.util.JwtUtil;

import java.util.Optional;
import java.util.UUID;

@Service
public class RedisUserServiceImpl implements RefreshTokenService {
    @Autowired
    private RedisUserRepository redisUserRepository;
    @Autowired
    private ApiAuthService apiAuthService;
    @Autowired
    private JwtUtil jwtUtil;

    public Pair<String, String> getRefreshAccessTokensWithRotation(String oldRefreshToken, String accessToken, String deviceName) {

        String newRefreshToken = rotateToken(oldRefreshToken, deviceName).orElseThrow(() -> new BadCredentialsException("Malicious user taked token!"));

        String accesToken = apiAuthService.generateAccessToken(jwtUtil.extractUsername(accessToken));

        return Pair.of(accessToken, newRefreshToken);

    }

    public Optional<String> rotateToken(String oldRefreshToken, String deviceName) {
        Optional<RedisUser> redisUser = redisUserRepository.findById(oldRefreshToken);

        if (redisUser.isPresent() && redisUser.get().getDeviceName().equals(deviceName)) {
            UUID newRefreshToken = UUID.randomUUID();
            redisUserRepository.delete(redisUser.get());
            redisUser.get().setRefreshToken(newRefreshToken.toString());
            redisUserRepository.save(redisUser.get());
            return Optional.of(newRefreshToken.toString());
        } else return Optional.empty();

    }

    public RedisUser addRedisUser(RedisUser redisUser) {
        return redisUserRepository.save(redisUser);
    }

}
