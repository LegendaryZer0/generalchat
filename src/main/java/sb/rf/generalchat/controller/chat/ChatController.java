package sb.rf.generalchat.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService chatMessageService;
    @Autowired
    private ChatService chatRoomService;

/*    @MessageMapping("/chat")
    public void processMessage(@Payload MessagesDto chatMessage) {
        var chatId = chatRoomService.getChatUUID(new Long[]{chatMessage.getFrom(),chatMessage.getTo()});

        chatMessage.setChatId(chatId.get());

        chatMessageService.sendMessage(chatMessage.converToMessage());

        messagingTemplate.convertAndSendToUser(
                chatMessage.getIdTo(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }*/

}
