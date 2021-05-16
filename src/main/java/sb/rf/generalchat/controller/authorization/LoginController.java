package sb.rf.generalchat.controller.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin() {
        log.info("Giving back the login page");
        return "Login";
    }

    @GetMapping("/loginFail")
    @ResponseBody
    public String loginFail(HttpServletResponse response) {
        response.setStatus(203);
        return "Неправильное имя пользователя или пароль";
    }

}
