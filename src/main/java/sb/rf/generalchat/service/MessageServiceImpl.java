package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.Chats;
import sb.rf.generalchat.model.Message;
import sb.rf.generalchat.model.MessageStatisticView;
import sb.rf.generalchat.repository.ChatsRepository;
import sb.rf.generalchat.repository.MessageRepository;
import sb.rf.generalchat.repository.MessageStatisticRepository;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService, MessageStatisticService {
    private final MessageRepository messageRepo;
    private final UserService userService;

    private final MessageStatisticRepository statisticRepository;

    private final ChatsRepository chatsRepository;

    @Value("${welcome.message}")
    private String WELCOME_MESSAGE;

    public MessageServiceImpl(MessageRepository messageRepo, UserService userService, ChatsRepository repository, MessageStatisticRepository statisticRepository) {
        this.messageRepo = messageRepo;
        this.userService = userService;
        this.chatsRepository = repository;
        this.statisticRepository = statisticRepository;
    }

    public List<MessageStatisticView> getMessageStatistic() {
        return statisticRepository.findAll();
    }

    public void sendMessage(Message message) {

        message.setIdFrom(message.getIdFrom());
        message.setIdTo(message.getIdTo());
        log.info("Save message {}", message);
        messageRepo.save(message);
        Chats chat = chatsRepository.save(Chats.builder().idFrom(message.getIdFrom())
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
        this.sendMessage(Message.builder().idTo(user_id)
                .idFrom(user_id)
                .message(WELCOME_MESSAGE)
                .time(new Timestamp(System.currentTimeMillis()))
                .build());
    }

}
