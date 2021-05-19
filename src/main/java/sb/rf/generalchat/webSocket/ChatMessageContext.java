package sb.rf.generalchat.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.model.dto.MessagesDto;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Component
@Slf4j
public class ChatMessageContext {


    private final Map<User, Set<Session>> sessions = new ConcurrentHashMap<>();



    void add(Session session, User user) {
        sessions.computeIfAbsent(user, v -> ConcurrentHashMap.newKeySet()).add(session);
    }

    void remove(Session session) {
        sessions.values().forEach(v -> v.removeIf(e -> e.equals(session)));
    }


    public void send(User user, MessagesDto message) {
        Set<Session> userSessions;

        log.info("recipient {}",user);
        log.info("message to send {}",message);
        log.info("sessions of all {}",sessions);
        synchronized(sessions) {
            userSessions = sessions.entrySet().stream()
                    .filter(e ->
                    {

                        log.info( "userTo id: {}   user from sessionSet ID {}",user.getId(),e.getKey().getId());

                       return user.getId().equals(e.getKey().getId());
                    })
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toSet());
        }
        log.info("usersessions set {}",userSessions.toString());

        for (Session userSession : userSessions) {
            if (userSession.isOpen()) {
                log.info("Session opened, send message");
                userSession.getAsyncRemote().sendObject(message);
            }
        }
    }

}
