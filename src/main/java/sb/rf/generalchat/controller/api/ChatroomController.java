package sb.rf.generalchat.controller.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.rf.generalchat.model.Chats;
import sb.rf.generalchat.model.dto.ChatroomStateDto;
import sb.rf.generalchat.service.ChatService;

@RestController
@RequestMapping("/api")
@Slf4j
public class ChatroomController {

    @Autowired
    ChatService chatService;
    @PostMapping("/chatroom/ban")
    public void banChatroom(@RequestBody ChatroomStateDto stateDto){
        chatService.setChatroomState(stateDto.getId_from(), stateDto.getId_to(), Chats.State.BANNED);
    }
}
