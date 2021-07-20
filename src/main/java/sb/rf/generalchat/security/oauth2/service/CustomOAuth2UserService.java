package sb.rf.generalchat.security.oauth2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.repository.UserJpaRepository;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  @Autowired private UserJpaRepository userJpaRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User user = super.loadUser(userRequest);
    log.info("coming oauth2 user is : {}", user);
    return user;
  }
}
