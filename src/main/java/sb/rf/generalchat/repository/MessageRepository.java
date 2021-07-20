package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.rf.generalchat.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findAllByIdFromAndIdTo(long idFrom, long idTo);
}
