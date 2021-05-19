package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sb.rf.generalchat.model.Chats;

import java.util.List;
import java.util.UUID;

public interface ChatsRepository extends JpaRepository<Chats, Long> {


    @Query(value = "select chat.state from Chats  chat  where  ((chat.idFrom=:id_from and  chat.idTo=:id_to)) "
    )
    String getChatroomState(@Param("id_from") Long aLong, @Param("id_to") Long aLong1);

    @Query(nativeQuery = true,value = "update chats set chats.blocked=:state where ((chats.id_from=:id_from and chats.id_to=:id_to) or (chats.id_from==:id_to and chats.id_to=:id_from))")
    void setChatroomState(@Param("id_from") Long aLong, @Param("id_to") Long aLong1,@Param("state") String state);

    Chats getChatsByIdFromAndIdTo(@Param("id_from") Long aLong, @Param("id_to") Long aLong1);

}
