package sb.rf.generalchat.controller.messenger;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.security.SessionUserInfo;
import sb.rf.generalchat.service.ChatService;
import sb.rf.generalchat.service.MessageService;
import sb.rf.generalchat.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user")
public class MessengerController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    private User user;

    @Autowired
    private ChatService chatService;

    @Autowired
    private SessionUserInfo sessionUserInfo;


    @PostMapping("/getInfo")
    @ResponseBody
    public List<MessagesDto> getMesagesBetweenTwoUsers(@RequestBody Pair pair){
        log.info("Пара ID {}",pair);
        log.info("Отработал");
        List<Message> messages = messageService.getAllMessagesBetweenTwoUsers(pair.getId_from(), pair.getId_to());

        log.info("Сообщения{}",messages);
        List<MessagesDto> messagesDto = MessagesDto.from(messages);
        log.info(" MessagesDTO {}",messagesDto);
        return messagesDto;
    }

    @PostMapping("/giveUrl")
    @ResponseBody
    public UUID getUUIDForUsers(@RequestBody Long[] data){

        UUID uuid =  chatService.getChatUUID(data);
        log.info("uuid отправлено : {}",uuid );
        return uuid;

    }


    @GetMapping("/messenger")
    public String getAllUsersChats(HttpServletResponse response, HttpServletRequest request){
        user = sessionUserInfo.getCurrentUser();
        log.info("user : {}",user);
        log.info("user service is {}",userService);
        List<User> users = userService.findAllUsersChats(user.getId());
        if (users.isEmpty()) {
            users.add(user);
        }
        log.info("CURRENT USER IS {}",user.toString());
        request.getSession().setAttribute("ListChats", users);
        request.getSession().setAttribute("mainUser", user);
        log.info("Users chats {}", users);
        return "Messenger";

    }
    @Data
    private  static  class Pair {
        private long id_from;
        private long id_to;
    }

}
