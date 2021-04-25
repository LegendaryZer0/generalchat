/*
package sb.rf.generalchat.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.rf.generalchat.model.dto.UserLoginDto;
import sb.rf.generalchat.security.util.JwtUtil;

import javax.annotation.security.PermitAll;

@RestController
@Slf4j
public class AuthenticateController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Qualifier("activeUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    @PermitAll
    public ResponseEntity<?> createToken(@RequestBody UserLoginDto userLoginDto){
        try{
            log.info("user ti authenticate {}",userLoginDto);
            log.info("authentic manager {}",authenticationManager);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getLogin(),userLoginDto.getPassword()));

        }catch (BadCredentialsException e){
            throw  new IllegalStateException(e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userLoginDto.getLogin());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }
}
*/
