package sb.rf.generalchat.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;

public class CustomOidcUser extends DefaultOidcUser {
  private OidcUser user;

  public CustomOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken) {
    super(authorities, idToken);
  }

  public CustomOidcUser(
      Collection<? extends GrantedAuthority> authorities,
      OidcIdToken idToken,
      String nameAttributeKey) {
    super(authorities, idToken, nameAttributeKey);
  }

  public CustomOidcUser(
      Collection<? extends GrantedAuthority> authorities,
      OidcIdToken idToken,
      OidcUserInfo userInfo) {
    super(authorities, idToken, userInfo);
  }

  public CustomOidcUser(
      Collection<? extends GrantedAuthority> authorities,
      OidcIdToken idToken,
      OidcUserInfo userInfo,
      String nameAttributeKey) {
    super(authorities, idToken, userInfo, nameAttributeKey);
  }
}
