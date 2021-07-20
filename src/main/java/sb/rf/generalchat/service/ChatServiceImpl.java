package sb.rf.generalchat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.Chats;
import sb.rf.generalchat.repository.ChatsRepository;
import sb.rf.generalchat.repository.UserJpaRepository;

import java.util.UUID;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

  private UserJpaRepository userRepository;
  private UUID uuid;
  private ChatsRepository chatsRepository;

  public ChatServiceImpl() {}

  @Autowired
  public ChatServiceImpl(UserJpaRepository userRepository, ChatsRepository repository) {
    super();
    this.userRepository = userRepository;
    this.chatsRepository = repository;
  }

  public String getChatroomState(Long id_from, Long id_to) {
    log.info("id from {} , id to {}", id_from, id_to);
    return chatsRepository.getChatroomState(id_from, id_to);
  }

  public void setChatroomState(Long id_from, Long id_to, Chats.State state) {
    log.info("id from {} , id to {} state {}", id_from, id_to, state);
    chatsRepository.setChatroomState(id_from, id_to, state.name());
  }
}
