package sb.rf.generalchat.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    private ApplicationContext applicationContext /*= new ClassPathXmlApplicationContext("WEB-INF/dispatcherServlet-servlet.xml")*/;
    private static final Set<ChatServerEndPoint> sessionsSet
            = new CopyOnWriteArraySet<>();

   /* @Qualifier("userService")*/
/*    private  UserService userServiceStatic*//*=applicationContext.getBean("userServiceImpl",UserService.class)*//*;*/
    @Autowired
    public  void setUserService(UserService userService) {
        ChatServerEndPoint.userService = userService;
    }


    private static UserService userService;

   /* @Qualifier("messageService")*/
      //Todo разобраться с autowired
    private static MessageService messageService/*= applicationContext.getBean("messageServiceImpl",MessageService.class);*/;
    @Autowired
    public  void setMessageService(MessageService messageService) {
        ChatServerEndPoint.messageService = messageService;
    }

    private String sessionId;
    private Session session;
    public ChatServerEndPoint(UserService userService, MessageService messageService){
        this.userService = userService;
        this.messageService =messageService;
    }
    public ChatServerEndPoint(){

    }

    /* private static HashMap<String, String> users = new HashMap<>();*/

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("hash") String hash/*,@PathParam("")String id_from,@PathParam("") String id_to*/) throws IOException {
        this.session = session;
        sessionId = hash;


        sessionsSet.add(this);
        log.info("Сессия открыта сессий всего {}", sessionsSet.size());
        log.info("Идентификатор сессии{}", hash);
        log.info("Сервисы инициализированы {}   {} ",messageService,userService);

    }

    @OnMessage
    public void onMessage(Session session, MessagesDto message) {
        log.info("Количество сессий на момент отпр сообщ {}", sessionsSet.size());
        log.info("Сообщение в onMessage перед отправкой  {}", message.toString());
        messageService.sendMessage(message.converToMessage());
        sessionsSet.stream().forEach(x -> {
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

        sessionsSet.remove(this);
        log.info("Сессий после закрытия {}", sessionsSet.size());

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new IllegalStateException(throwable);
    }


}
