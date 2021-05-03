package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.GoogleOpenIdUser;
import sb.rf.generalchat.model.TechnicalInfo;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.repository.GoogleOidcRepo;
import sb.rf.generalchat.repository.UserJpaRepository;
import sb.rf.generalchat.security.details.UserDetailsImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Slf4j
public class GoogleOidcServiceImpl implements GoogleOidcService{
    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    GoogleOidcRepo googleOidcRepo;

    @Autowired
    AuthenticationManager manager;

    @Qualifier("activeUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MessageService messageService;


    @Override
    public void processOAuthPostLogin(OidcUser oidcUser, HttpServletRequest request) {
        log.info("Now i need save , {}",oidcUser);
        Optional<User> userAccount =userRepository.getUserByEmail(oidcUser.getEmail());
        if(userAccount.isEmpty()){  //два случая, пуст из-за того, чтоюзеравообще нет, и потому чот есть, однако с невалидным email-ом
            Optional<GoogleOpenIdUser> googleOpenIdUserFromRepo =googleOidcRepo.findById(oidcUser.getName());//Если и его нет, тоюзера точно нет
            if (googleOpenIdUserFromRepo.isEmpty()) {

                //если юзера вообще нет
                GoogleOpenIdUser googleOpenIdUser = GoogleOpenIdUser.builder()
                        .oidcUser(oidcUser)
                        .email(oidcUser.getEmail())
                        .name(oidcUser.getName())
                        .nickname(oidcUser.getFullName())
                        .account_user(User.builder()
                                .email(oidcUser.getEmail())
                                .nickname(oidcUser.getFullName())
                                .password(" ")  //todo разобраться,как в этом случае лучше сохранять пароль

                                .role(User.Role.USER)
                                .state(User.State.ACTIVE)
                                .technicalInfo(TechnicalInfo.builder()
                                        .isDeleted(false)
                                        .confirmState(TechnicalInfo.ConfirmState.CONFIRMED)
                                        .build())
                                .build()).build();

                GoogleOpenIdUser googleUser =googleOidcRepo.save(googleOpenIdUser);
                messageService.sendWelcomeMessage(googleUser.getAccount_user().getId());
                setAuthenticationForce(googleOpenIdUser,request);

            }else { //Todo убрать этот тихий ужас
                //юзер есть, но он когда-то поменял email
                googleOpenIdUserFromRepo.get().getAccount_user().setEmail(oidcUser.getEmail());
                googleOidcRepo.save(googleOpenIdUserFromRepo.get());
            }
        }else {
            //юзер однозначно есть
            GoogleOpenIdUser googleOpenIdUser = GoogleOpenIdUser.builder()
                    .oidcUser(oidcUser)
                    .email(oidcUser.getEmail())
                    .name(oidcUser.getName())
                    .nickname(oidcUser.getFullName()).build();
            userAccount.get().setGoogleOpenIdUser(googleOpenIdUser);
            userRepository.updateUser(userAccount.get(),userAccount.get().getId());
            setAuthenticationForce(userAccount.get(),request);
            log.info("success");
        }
        log.info("Iended my work");
    }

    private void setAuthenticationForce(GoogleOpenIdUser googleOpenIdUser,HttpServletRequest request){
        UserDetails userDetails = userDetailsService.loadUserByUsername(googleOpenIdUser.getEmail());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
       /* Authentication auth = manager.authenticate(usernamePasswordAuthenticationToken);//setted authentication by force
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);*/
    }
    private void setAuthenticationForce(User user,HttpServletRequest request){
        log.info("sterted savinf user {}",user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
       /* Authentication auth = manager.authenticate(usernamePasswordAuthenticationToken);//setted authentication by force
        log.info("authenticationwas good {}",auth.isAuthenticated());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);*/
        log.info("ended");
    }
}
