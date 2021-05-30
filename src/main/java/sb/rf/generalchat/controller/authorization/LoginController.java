package sb.rf.generalchat.controller.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public ResponseEntity<?> loginFail() {

        return ResponseEntity.status(203).body("Неправильное имя пользователя или пароль");
    }

}
