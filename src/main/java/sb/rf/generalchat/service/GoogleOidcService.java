package sb.rf.generalchat.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.servlet.http.HttpServletRequest;

public interface GoogleOidcService {
    public void processOAuthPostLogin(OidcUser oauthUser, HttpServletRequest request);
}
