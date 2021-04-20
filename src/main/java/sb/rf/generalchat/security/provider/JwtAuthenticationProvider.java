/*
package sb.rf.generalchat.security.provider;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.security.details.UserDetailsImpl;
import sb.rf.generalchat.util.JWTUtil;

import java.util.List;

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) ((AbstractAuthenticationToken)authentication);

        String token = jwtAuthenticationToken.getToken().toString();

        User parsedUser = jwtUtil.parseToken(token);

        if (parsedUser == null) {
            throw new IllegalStateException("JWT token is not valid");
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole().name());

        return new UserDetailsImpl(parsedUser);
    }

}*/
