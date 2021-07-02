package sb.rf.generalchat.service;

import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.dto.MessagesDto;

import java.util.List;

public interface MessageService {
  void recieveAndSaveStringMessage(Message message);

  List<Message> getAllMessagesFromUserToUser(long id_From, long id_to);

  List<Message> getAllMessagesBetweenTwoUsers(long id_From, long id_to);
  void saveDocumentMessage(MessagesDto messagesDto);
  default void sendWelcomeMessage(Long id_user) {};
  public String getImage(String uuid);
}
