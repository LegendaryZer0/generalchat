package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.rf.generalchat.model.BasicOpenIdUser;

public interface GoogleOidcRepo extends JpaRepository<BasicOpenIdUser, String> {}
