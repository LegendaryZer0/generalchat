package sb.rf.generalchat.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sb.rf.generalchat.model.dto.UserLoginDto;
import sb.rf.generalchat.security.util.JwtUtil;
import sb.rf.generalchat.service.ApiAuthService;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;

@RestController
@Slf4j
public class AuthenticateController {


    @Qualifier("activeUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ApiAuthService apiAuthService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    @PermitAll
    public ResponseEntity<?> resolveAccess(@RequestBody UserLoginDto userLoginDto, Device device, HttpServletResponse response) {
        try {

            log.info("user ti authenticate {}", userLoginDto);
            apiAuthService.processUser(userLoginDto, response, device.getDevicePlatform().name());
        } catch (BadCredentialsException e) {
            throw new IllegalStateException(e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userLoginDto.getLogin());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }
}
