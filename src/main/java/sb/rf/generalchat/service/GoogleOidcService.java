package sb.rf.generalchat.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import javax.servlet.http.HttpServletRequest;

public interface GoogleOidcService {
  void processOAuthPostLogin(OidcUser oauthUser, HttpServletRequest request);
}
