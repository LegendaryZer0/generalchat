package sb.rf.generalchat.controller.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.service.ChatService;
import sb.rf.generalchat.service.MessageService;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService chatMessageService;
    @Autowired
    private ChatService chatRoomService;

    @MessageMapping("/chatroom/")
    @SendTo("/chat/{uuid}")
    public void processMessage(@Payload MessagesDto chatMessage) {
        log.info("message: {} ",chatMessage);
        chatMessageService.recieveAndSaveStringMessage(chatMessage.converToMessage());
    }

}
