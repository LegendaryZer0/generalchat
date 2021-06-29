package sb.rf.generalchat.controller.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sb.rf.generalchat.service.UserService;

@Controller
public class MailController {
  @Autowired UserService userService;

  @GetMapping("/confirm")
  public String confirmAccount(@RequestParam Long id) {
    userService.confirmEmail(id);
    return "Login";
  }
}
