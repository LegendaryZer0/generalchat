package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.*;
import sb.rf.generalchat.model.dto.MessagesDto;
import sb.rf.generalchat.repository.ChatsRepository;
import sb.rf.generalchat.repository.DocumentRepo;
import sb.rf.generalchat.repository.MessageRepository;
import sb.rf.generalchat.repository.MessageStatisticRepository;
import sb.rf.generalchat.webSocket.ChatMessageContext;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService, MessageStatisticService {
  private final MessageRepository messageRepo;
  private final UserService userService;


  private final MessageStatisticRepository statisticRepository;

  private final ChatsRepository chatsRepository;
  private DocumentRepo documentRepo;
  @Autowired private ChatMessageContext chatMessageContext;

  @Value("${welcome.message}")
  private String WELCOME_MESSAGE;

  public MessageServiceImpl(
      MessageRepository messageRepo,
      UserService userService,
      ChatsRepository repository,
      MessageStatisticRepository statisticRepository,
      DocumentRepo documentRepo) {
    this.messageRepo = messageRepo;
    this.userService = userService;
    this.chatsRepository = repository;
    this.statisticRepository = statisticRepository;
    this.documentRepo = documentRepo;
  }

  public List<MessageStatisticView> getMessageStatistic() {
    return statisticRepository.findAll();
  }

  public void recieveAndSaveStringMessage(Message message) {

    message.setIdFrom(message.getIdFrom());
    message.setIdTo(message.getIdTo());
    log.info("Save message {}", message);
    messageRepo.save(message);
    Chats chat =
        chatsRepository.save(
            Chats.builder()
                .idFrom(message.getIdFrom())
                .idTo(message.getIdTo())
                .state(Chats.State.ACTIVE)
                .build());
    log.info("created chat {}", chat);
  }

  public List<Message> getAllMessagesFromUserToUser(long id_From, long id_to) {
    List<Message> messages = messageRepo.findAllByIdFromAndIdTo(id_From, id_to);
    messages.sort(Comparator.comparing(Message::getTime));
    return messages;
  }

  public List<Message> getAllMessagesBetweenTwoUsers(long id_From, long id_to) {
    log.info("Message repo {}", messageRepo);
    List<Message> messagesFrom = messageRepo.findAllByIdFromAndIdTo(id_From, id_to);
    if (id_From != id_to) {
      List<Message> messagesTo = messageRepo.findAllByIdFromAndIdTo(id_to, id_From);
      messagesFrom.addAll(messagesTo);
      log.info("messagesFrom and to  from: {} to:  {}", messagesFrom, messagesTo);
    }
    messagesFrom.sort(Comparator.comparing(Message::getTime));
    log.info(messagesFrom.toString());

    return messagesFrom;
  }

  @Override
  public void sendWelcomeMessage(Long user_id) {
    log.info("WELCOME MESSAGE {}", WELCOME_MESSAGE);
    this.recieveAndSaveStringMessage(
        Message.builder()
            .idTo(user_id)
            .idFrom(user_id)
                .messageType(Message.MessageType.STRING)
            .message(WELCOME_MESSAGE)
            .time(new Timestamp(System.currentTimeMillis()))
            .build());
  }
  public void saveDocumentMessage(MessagesDto messagesDto){
  //  log.info("messageDto {}",messagesDto);
    UUID uuid = UUID.randomUUID();
    Message message = messagesDto.converToMessage();
    message.setMessage(uuid.toString());
    message.setMessageType(messagesDto.converToMessage().getMessageType());
    recieveAndSaveStringMessage(message);

    documentRepo.save(Document.builder().link(uuid).data(messagesDto.getMessage()).build());


    User user = User.builder().id(message.getIdTo()).build();
    log.info("user to send photo {}",user);
    messagesDto.setMessage(uuid.toString());
    log.info("messageDto to send {}",messagesDto);
    chatMessageContext.send(user,messagesDto);

  }
  public String getImage(String uuid){
    Optional<Document> document = documentRepo.findById(UUID.fromString(uuid));
    if(document.isPresent()){
      log.info("body size {}",document.get().getData().length());
      return document.get().getData();
    }else {
      throw new IllegalStateException();
    }


  }
}
