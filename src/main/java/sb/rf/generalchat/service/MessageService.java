package sb.rf.generalchat.service;


import sb.rf.generalchat.model.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message);

    List<Message> getAllMessagesFromUserToUser(long id_From, long id_to);

    List<Message> getAllMessagesBetweenTwoUsers(long id_From, long id_to);

    default void sendWelcomeMessage(Long id_user) {

    }

}
