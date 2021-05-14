package sb.rf.generalchat.service;



import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.security.oauth2.CustomOAuth2User;
import sb.rf.generalchat.security.oauth2.CustomOidcUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> findAllUsersChats(long id);
    public User updateUser(User user);

    public User getUserById(long id);
    public Optional<User> getUser(String login, String password);
    public User getUserByEmail(User user);
    public User addUser(User user);
    public void confirmEmail(long id);
    public List<User> getAllUsers();
    public Integer deleteUser(String email);
    public void deleteUserForceById(Long id);
    public List<User> getAllUsersForce();


    void processOAuthPostLogin(OAuth2User oauthUser);
}
