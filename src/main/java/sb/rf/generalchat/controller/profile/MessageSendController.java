package sb.rf.generalchat.controller.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.FirstMessageDto;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.model.dto.UserChangeSettingsDto;
import sb.rf.generalchat.service.MessageService;
import sb.rf.generalchat.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@Slf4j
@Controller
@RequestMapping("/user")
public class MessageSendController {
  private User userTo;
  private User userFrom;
  private User user;
  private Message message;
  @Autowired private MessageService messageService;
  @Autowired private UserService userService;

  @PostMapping("/sendMessage")
  public String sendMessage(
      HttpServletRequest request,
      @ModelAttribute("messageForm") @Valid FirstMessageDto messageForm,
      BindingResult bindingResult,
      Model model) {
    log.info("Coming message dto {}", messageForm);
    if (!bindingResult.hasErrors()) {
      log.info("No errors");
      user = (User) request.getSession().getAttribute("user");
      log.info(user.toString());
      userTo = userService.getUserByEmail(messageForm.getUser());
      message =
          Message.builder()
              .idFrom(user.getId())
              .idTo(userTo.getId())
              .time(new Timestamp(System.currentTimeMillis()))
              .message(messageForm.getMessage())
                  .messageType(Message.MessageType.STRING)
              .build();
      log.info("Сообщение : {}", message.toString());
      messageService.recieveAndSaveStringMessage(message);
      log.info("The data entered is correct, go ahead");
      return "redirect:/user/selfProfile";
    } else {

      log.info("Sended dto {}", messageForm.toString());
      bindingResult
          .getAllErrors()
          .forEach(x -> log.info("The errors are listed here  {}", x.toString()));
      log.info("The data entered is incorrect, showing an error");
      model.addAttribute("messageForm", messageForm);
      model.addAttribute("userSettingsForm", new UserChangeSettingsDto());
      return "Profile";
    }
  }
  @PostMapping("/sendDocument")

  public ResponseEntity<?> receiveDocument(@RequestBody MessagesDto messagesDto){
    messageService.saveDocumentMessage(messagesDto);
    return ResponseEntity.ok("complete");//Todo - сохранение документов в отдельную таблицу (Document, например) с последующим возвращением  ссылки, по которой можно получить этот документ, эта ссылка должа сохраниться в Messages
  }
  @ResponseBody
  @GetMapping("/getDocument/{documentUUID}")
  public ResponseEntity<?> getDocument(@PathVariable("documentUUID") String uuid){
    log.info("link to document is {}",uuid);
    String imageVal = messageService.getImage(uuid);
    return ResponseEntity.status(200).contentLength(imageVal.length()).body(imageVal);
  }

}
