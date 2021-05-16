package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.repository.ChatsRepository;
import sb.rf.generalchat.repository.UserJpaRepository;

import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    private UserJpaRepository userRepository;
    private UUID uuid;
    private ChatsRepository chatsRepository;

    public ChatServiceImpl() {

    }

    @Autowired
    public ChatServiceImpl(UserJpaRepository userRepository, ChatsRepository repository) {
        super();
        this.userRepository = userRepository;
        this.chatsRepository = repository;
    }


    public UUID getChatUUID(Long[] pair) {
        Arrays.sort(pair);
        log.info("started gettingChatUUID");
        try {
            log.info("ChatRepository {}", chatsRepository);
            log.info("Pair {}", Arrays.toString(pair));
            log.info("pair {},  chatroomUUID {}", pair, chatsRepository.getChatroomUUIDForTwoUsers(pair[1], pair[0]));
            return chatsRepository.getChatroomUUIDForTwoUsers(pair[0], pair[1]).stream().findFirst().get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }


    }
}
