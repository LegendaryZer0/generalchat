package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import sb.rf.generalchat.model.GoogleOpenIdUser;

public interface GoogleOidcRepo extends JpaRepository<GoogleOpenIdUser,String> {

}
