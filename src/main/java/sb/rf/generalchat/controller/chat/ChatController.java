package sb.rf.generalchat.controller.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.service.ChatService;
import sb.rf.generalchat.service.MessageService;

import java.util.UUID;
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
    public void processMessage(@Payload MessagesDto chatMessage,@DestinationVariable("uuid") String uuid) {
        log.info("message: {}  uuid:  {}",chatMessage,uuid);
        chatMessageService.sendMessage(chatMessage.converToMessage());
    }

}
