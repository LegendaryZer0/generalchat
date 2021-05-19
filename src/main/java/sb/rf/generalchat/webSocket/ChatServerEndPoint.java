package sb.rf.generalchat.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.service.MessageService;
import sb.rf.generalchat.service.UserService;
import sb.rf.generalchat.webSocket.output.format.Decoder;
import sb.rf.generalchat.webSocket.output.format.Encoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Slf4j
@ServerEndpoint(value = "/chat/chatendpoint",
        decoders = Decoder.class,
        encoders = Encoder.class,
        configurator = UserAwareConfigurator.class
)
@Component
public class ChatServerEndPoint {
    private static final Set<ChatServerEndPoint> sessionsEndpointsSet
            = new CopyOnWriteArraySet<>();


    private static UserService userService;
    private static MessageService messageService;
    private static ChatMessageContext chatMessageContext;
    private String sessionId;
    private Session session;

    public ChatServerEndPoint(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }
    public ChatServerEndPoint() {

    }

    @Autowired
    public void setUserService(UserService userService) {
        ChatServerEndPoint.userService = userService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        ChatServerEndPoint.messageService = messageService;
    }
    @Autowired
    public void setChatMessageContext(ChatMessageContext chatMessageContext){ChatServerEndPoint.chatMessageContext = chatMessageContext;};

    @OnOpen
    public void onOpen(
            Session session,
            EndpointConfig endpointConfig
           ) {
        User user = (User) endpointConfig.getUserProperties().get("user");
        log.info("user joined to chat  {}",user);
        this.session = session;

        sessionsEndpointsSet.add(this);
        chatMessageContext.add(session,user);
        log.info("The session is open to all {}", sessionsEndpointsSet.size());
        log.info("Services initializedы {}   {} ", messageService, userService);

    }

    @OnMessage
    public void onMessage(Session session, MessagesDto message) {
        log.info("Number of sessions at the time of sending {}", sessionsEndpointsSet.size());
        log.info("Message in onMessage before sending  {}", message.toString());
        User userTo= User.builder().id(message.getTo()).build();
        chatMessageContext.send(userTo,message);
        messageService.sendMessage(message.converToMessage());

    }

    @OnClose
    public void onClose(Session session) {
        chatMessageContext.remove(session);
        sessionsEndpointsSet.remove(this);
        log.info("Sessions after onClose method {}", sessionsEndpointsSet.size());

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new IllegalStateException(throwable);
    }


}
