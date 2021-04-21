package sb.rf.generalchat.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.service.UserService;

@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class SessionUserInfo {
    private User user;

    @Autowired
    private UserService userService;

    public void setUserForce(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        if (user == null) {
            String email =((UserDetails) (SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal()))
                    .getUsername();

            log.info("email in session : {}   userService :: {}",email,userService);
            user = userService.getUserByEmail(
                    User.builder()
                            .email(email)
                            .build()
            );
            log.info("returned user from session info {}",user);
        }
        return user;
    }
}
