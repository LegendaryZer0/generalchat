package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sb.rf.generalchat.model.BasicOpenIdUser;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.repository.GoogleOidcRepo;
import sb.rf.generalchat.repository.UserJpaRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Slf4j
public class GoogleOidcServiceImpl implements GoogleOidcService {
  @Autowired GoogleOidcRepo googleOidcRepo;
  @Autowired AuthenticationManager manager;
  @Autowired private UserJpaRepository userRepository;

  @Qualifier("activeUserDetailsService")
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired private MessageService messageService;

  @Override
  public void processOAuthPostLogin(OidcUser oidcUser, HttpServletRequest request) {
    log.info("Now i need save , {}", oidcUser);
    Optional<User> userAccount = userRepository.getUserByEmail(oidcUser.getEmail());
    if (userAccount
        .isEmpty()) { // два случая, пуст из-за того, чтоюзеравообще нет, и потому чот есть, однако
                      // с невалидным email-ом
      Optional<BasicOpenIdUser> googleOpenIdUserFromRepo =
          googleOidcRepo.findById(oidcUser.getName()); // Если и его нет, тоюзера точно нет
      if (googleOpenIdUserFromRepo.isEmpty()) {
        // если юзера вообще нет
        createUserAccountFromGoogleOidc(oidcUser, request);

      } else {
        // юзер есть, но он когда-то поменял email
        googleOpenIdUserFromRepo.get().getAccount_user().setEmail(oidcUser.getEmail());
        googleOidcRepo.save(googleOpenIdUserFromRepo.get());
      }
    } else {
      // юзер однозначно есть
      updateUsersGoogleOidcPart(oidcUser, userAccount.get(), request);
      log.info("success");
    }
    log.info("Iended my work");
  }

  private void setAuthenticationForce(BasicOpenIdUser basicOpenIdUser, HttpServletRequest request) {

    UserDetails userDetails = userDetailsService.loadUserByUsername(basicOpenIdUser.getEmail());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  private void setAuthenticationForce(User user, HttpServletRequest request) {
    log.info("sterted savinf user {}", user);
    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    log.info("ended");
  }

  @Transactional
  private void createUserAccountFromGoogleOidc(OidcUser oidcUser, HttpServletRequest request) {
    BasicOpenIdUser basicOpenIdUser = BasicOpenIdUser.from(oidcUser);
    BasicOpenIdUser googleUser = googleOidcRepo.save(basicOpenIdUser);
    messageService.sendWelcomeMessage(googleUser.getAccount_user().getId());
    setAuthenticationForce(basicOpenIdUser, request);
  }

  @Transactional
  private void updateUsersGoogleOidcPart(
      OidcUser oidcUser, User userAccount, HttpServletRequest request) {
    BasicOpenIdUser basicOpenIdUser =
        BasicOpenIdUser.builder()
            .oidcUser(oidcUser)
            .email(oidcUser.getEmail())
            .name(oidcUser.getName())
            .nickname(oidcUser.getFullName())
            .build();
    userAccount.setBasicOpenIdUser(basicOpenIdUser);
    userRepository.updateUser(userAccount, userAccount.getId());
    setAuthenticationForce(userAccount, request);
  }
}
