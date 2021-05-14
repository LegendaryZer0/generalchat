package sb.rf.generalchat.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.security.authentication.JWTAuthentication;
import sb.rf.generalchat.security.util.JwtUtil;

@Component
@Slf4j
public class JWTAuthProvider implements AuthenticationProvider {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("ProviderStartedToWork");
        JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
        jwtUtil.extractUsername(authentication.getName());
        jwtAuthentication.setAuthenticated(true);
        jwtAuthentication.setAuthority(jwtUtil.extractClaim(authentication.getName(), claims -> claims.get("role", String.class)));

        log.info(jwtAuthentication.getAuthorities().toString());
        log.info("jwtAuthentication  {}", jwtAuthentication);
        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return JWTAuthentication.class.equals(authentication);
    }
}
