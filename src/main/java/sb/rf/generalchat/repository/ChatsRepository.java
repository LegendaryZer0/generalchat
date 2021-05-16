package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sb.rf.generalchat.model.Chats;

import java.util.List;
import java.util.UUID;

public interface ChatsRepository extends JpaRepository<Chats, Long> {


    @Query(value = "select chat.uuid from Chats  chat  where  ((chat.idFrom=:id_from and  chat.idTo=:id_to) or (chat.idFrom =:id_to and chat.idTo =:id_from)) "
    )
    List<UUID> getChatroomUUIDForTwoUsers(@Param("id_from") Long aLong, @Param("id_to") Long aLong1);
}
