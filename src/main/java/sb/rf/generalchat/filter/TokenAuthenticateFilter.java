//package sb.rf.generalchat.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import sb.rf.generalchat.security.authentication.JWTAuthentication;
//import sb.rf.generalchat.security.util.JwtUtil;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import java.util.Optional;
//@Slf4j
//@Component
//public class TokenAuthenticateFilter extends OncePerRequestFilter {
//    @Autowired
//    JwtUtil jwtUtil;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Optional<Cookie> accesTokenCookie = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("access_token")).findFirst();
//
//        if(response.getStatus()!=403){
//            String userName;
//            userName = jwtUtil.extractUsername(accesTokenCookie.get().getValue());
//
//            log.info("username from jwt is {}", userName);
//            JWTAuthentication tokenAuthentication = new JWTAuthentication(accesTokenCookie.get().getValue());
//            tokenAuthentication.setAuthenticated(true);
//            String authority = jwtUtil.extractClaim(accesTokenCookie.get().getValue(), claims -> {
//                log.info("AUTHORITY VALUES" + claims.get("role", LinkedHashMap.class).values());
//                return (String) claims.get("role", LinkedHashMap.class).values().stream().findFirst().get();
//            });
//            tokenAuthentication.setAuthority(authority);
//            log.info("Token authentication is {}", tokenAuthentication);
//            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
//        }
//        filterChain.doFilter(request,response);
//    }
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return !request.getRequestURI().contains("/api");
//    }
//}
