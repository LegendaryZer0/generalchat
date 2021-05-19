package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.rf.generalchat.model.MessageStatisticView;

public interface MessageStatisticRepository extends JpaRepository<MessageStatisticView,Long> {
}
