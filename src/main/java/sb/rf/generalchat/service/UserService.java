package sb.rf.generalchat.service;



import sb.rf.generalchat.model.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUsersChats(long id);
    public User updateUser(User user);

    public User getUserById(long id);
    public User getUser(String login, String password);
    public User getUserByEmail(User user);
    public User addUser(User user);
    public void confirmEmail(long id);
    public List<User> getAllUsers();
    public Integer deleteUser(String email);
    public void deleteUserForceById(Long id);
    public List<User> getAllUsersForce();




}
