package sb.rf.generalchat.filter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sb.rf.generalchat.security.authentication.JWTAuthentication;
import sb.rf.generalchat.security.util.JwtUtil;
import sb.rf.generalchat.service.TokenAuthenticateService;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Optional;

@Component
@Slf4j
public class AccesTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TokenAuthenticateService authenticateService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("accessFilterStartedToWork");
        Optional<Cookie> accesTokenCookie = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("access_token")).findFirst();
        if (accesTokenCookie.isPresent() && checkTokenExpiration(accesTokenCookie)) {
            authenticateService.authenticateUser(accesTokenCookie.get().getValue());
/*            String userName;
            userName = jwtUtil.extractUsername(accesTokenCookie.get().getValue());

            log.info("username from jwt is {}", userName);
            JWTAuthentication tokenAuthentication = new JWTAuthentication(accesTokenCookie.get().getValue());
            tokenAuthentication.setAuthenticated(true);
            String authority = jwtUtil.extractClaim(accesTokenCookie.get().getValue(), claims -> {
                log.info("AUTHORITY VALUES" + claims.get("role", LinkedHashMap.class).values());
                return (String) claims.get("role", LinkedHashMap.class).values().stream().findFirst().get();
            });
            tokenAuthentication.setAuthority(authority);
            log.info("Token authentication is {}", tokenAuthentication);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);*/
        } else {
            response.setStatus(403);
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkTokenExpiration(Optional<Cookie> accesTokenCookie){
        try{
            jwtUtil.isTokenExpired(accesTokenCookie.get().getValue());
            return true;
        }catch (ExpiredJwtException e){
            log.info("token expired, return false");
            String username =e.getClaims().get("sub",String.class);
            log.info("sub cliam {}",username);
            return false;
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().contains("/api");
    }
}
