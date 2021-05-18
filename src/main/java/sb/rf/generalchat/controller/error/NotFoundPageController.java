package sb.rf.generalchat.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundPageController {
    @GetMapping("/error/404")
    public String return404Page(){
        return "404ErrorPage";
    }

}
