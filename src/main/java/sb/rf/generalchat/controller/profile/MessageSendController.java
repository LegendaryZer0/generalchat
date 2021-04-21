package sb.rf.generalchat.controller.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.FirstMessageDto;
import sb.rf.generalchat.model.dto.UserChangeSettingsDto;
import sb.rf.generalchat.service.MessageService;
import sb.rf.generalchat.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;

@Slf4j
@Controller
@RequestMapping("/user")
public class MessageSendController {
    private User userTo;
    private User userFrom;
    private User user;
    private Message message;
    @Autowired
    private MessageService messageService;
    @Autowired

    private UserService userService;

    @PostMapping("/sendMessage")
    public String sendMessage(HttpServletRequest request,@ModelAttribute("messageForm") @Valid FirstMessageDto messageForm,
                              BindingResult bindingResult,Model model){
        log.info("Прбывшее message dto {}",messageForm);
        if(!bindingResult.hasErrors()){
            log.info("Ошибок нет");
            user = (User) request.getSession().getAttribute("user");
            log.info(user.toString());
            userTo = userService.getUserByEmail(messageForm.getUser());
            message = Message.builder().idFrom(user.getId()).idTo(userTo.getId()).time(new Timestamp(System.currentTimeMillis())).message(messageForm.getMessage()).build();
            log.info("Сообщение : {}",message.toString());
            messageService.sendMessage(message);
            log.info("Введенные данные коректны, иду дальше");
            return "redirect:/user/selfProfile";
        }else {

            log.info("Отправляемая dto {}",messageForm.toString());
            bindingResult.getAllErrors().forEach(x->log.info("Oшибки перечислены здесь  {}",x.toString()));
            log.info("Введенные данные некоректны, показываю ошибку");
            model.addAttribute("messageForm", messageForm);
            model.addAttribute("userSettingsForm",new UserChangeSettingsDto());
            return "Profile";
        }

    }
}
