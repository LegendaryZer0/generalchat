package sb.rf.generalchat.security.details;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.repository.UserJpaRepository;


@Slf4j
@Service("activeUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("users  email ::{}::!",username);
        User user = userJpaRepository.getUserByEmail(username).orElseThrow(()->{
            log.info("Theres NO User in Db");
          return   new UsernameNotFoundException("Usernot found");});
        log.info("Loaded user {}",user);
        return new UserDetailsImpl(user);
    }
}
