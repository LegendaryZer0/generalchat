package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.encryption.Ecnrypt;
import sb.rf.generalchat.encryption.EncryptImpl;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.repository.UserJpaRepository;
import sb.rf.generalchat.util.EmailUtil;
import sb.rf.generalchat.util.MailsGenerator;


import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserJpaRepository userRepository;

    private final Ecnrypt cipher = new EncryptImpl();
    @Autowired
    private MailsGenerator mailsGenerator;


    @Value("${server.url}")
    private String serverUrl;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private EmailUtil emailUtil;

    private User user;
    public UserServiceImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User addUser(User user) {
        try {
            user.setPassword(cipher.encrypt(user.getPassword()));
            log.info("reg user, {}",user);
            if (userRepository.getUserByEmail(user.getEmail()).isEmpty()) {
                user=userRepository.save(user);
            }
            log.info("user repo log info by email {},",userRepository.getUserByEmail(user.getEmail()).get());
            String confirmMail = mailsGenerator.getEmailforConfirm(serverUrl, userRepository.getUserByEmail(user.getEmail()).get().getId());
            emailUtil.sendEmail(user.getEmail(), "Регистрация", from, confirmMail);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public User getUserByEmail(User user) {
        try {
            return userRepository.getUserByEmail(user.getEmail()).get();
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }

    }

    public User getUser(String login, String password) {
        try {
            User user = userRepository.getUserByEmail(login).get();
            log.info("entered password,{} " ,password);
            log.info("User from db{}",user);
            if (cipher.check(password, user.getPassword())) {
                return user;
            } else return new User();
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }

    }

    public User getUserById(long id) {
        return userRepository.getUserById(id).get();
    }

  /*  public User findUser(UUID uuid) {

        return userRepository.findUserByTechnicalInfo_Uuid(uuid).get();

    } *///fixme Определить, где это использовалось (видимо, тоже не понадобится в скором времени)

    public List<User> findAllUsersChats(long id) {
        log.info("Users id");
        List<User> userList = userRepository.findAllUserChatsById(id);
        log.info("all users chats :",userList);
        return userList;
    }

    public User updateUser(User user) {//fixme user должен прилетать вместе с id
        log.info("description user update {}", user);


        if (user.getPassword().equals(userRepository.getUserByEmail(user.getEmail()).get().getPassword())) {
            log.info("Пользователь не менял пароля");
            userRepository.save(user);
            return user;
        }
        user.setPassword(cipher.encrypt(user.getPassword()));
        userRepository.save(user);
        return user;


    }
    public List<User> getAllUsers(){
       return userRepository.getAllByTechnicalInfo_IsDeletedFalse();
    }

    public List<User> getAllUsersForce(){
        return userRepository.findAll();
    }
    public Integer deleteUser(String email){
        return     userRepository.markUserAsDeleted(email);
    }
    public void deleteUserForceById(Long id){
        userRepository.deleteById(id);
    }



    public void confirmEmail(long id) {//TODO id, or User?
        userRepository.confirmUser(id, "CONFIRMED");
    }

}
