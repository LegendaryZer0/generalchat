package sb.rf.generalchat.service;


import org.springframework.security.oauth2.core.user.OAuth2User;
import sb.rf.generalchat.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsersChats(long id);

    User updateUser(User user);

    User getUserById(long id);

    Optional<User> getUser(String login, String password);

    User getUserByEmail(User user);

    User addUser(User user);

    void confirmEmail(long id);

    List<User> getAllUsers();

    Integer deleteUser(String email);

    void deleteUserForceById(Long id);

    List<User> getAllUsersForce();

    Integer findCountOfDailyChatedUsers();


    void processOAuthPostLogin(OAuth2User oauthUser);
}
