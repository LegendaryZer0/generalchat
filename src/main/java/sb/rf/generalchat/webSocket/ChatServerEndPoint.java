package sb.rf.generalchat.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.service.MessageService;
import sb.rf.generalchat.service.UserService;
import sb.rf.generalchat.webSocket.output.format.Decoder;
import sb.rf.generalchat.webSocket.output.format.Encoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@ServerEndpoint(value = "/chat/{hash}",
        decoders = Decoder.class,
        encoders = Encoder.class
)
@Component
public class ChatServerEndPoint {
    private static final Set<ChatServerEndPoint> sessionsEndpointsSet
            = new CopyOnWriteArraySet<>();


    private static UserService userService;
    private static MessageService messageService;
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

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("hash") String hash) {
        this.session = session;
        sessionId = hash;


        sessionsEndpointsSet.add(this);
        log.info("The session is open to all {}", sessionsEndpointsSet.size());
        log.info("Session ID {}", hash);
        log.info("Services initializedы {}   {} ", messageService, userService);

    }

    @OnMessage
    public void onMessage(Session session, MessagesDto message) {
        log.info("Number of sessions at the time of sending {}", sessionsEndpointsSet.size());
        log.info("Message in onMessage before sending  {}", message.toString());
        messageService.sendMessage(message.converToMessage());
        sessionsEndpointsSet.stream().forEach(x -> {
            try {
                log.info(message.toString());
                if (!session.equals(x.session) && this.sessionId.equals(x.sessionId)) {
                    x.session.getBasicRemote().sendObject(message);
                }
            } catch (IOException | EncodeException e) {
                throw new IllegalStateException(e);
            }
        });

    }

    @OnClose
    public void onClose(Session session) {

        sessionsEndpointsSet.remove(this);
        log.info("Sessions after onClose method {}", sessionsEndpointsSet.size());

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new IllegalStateException(throwable);
    }


}
