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

    @Query(nativeQuery = true,value = "update chats set state=:state where ((id_from=:id_from and id_to=:id_to) or (id_from=:id_to and id_to=:id_from)) RETURNING 2")
    int setChatroomState(@Param("id_from") Long aLong, @Param("id_to") Long aLong1,@Param("state") String state);

    Chats getChatsByIdFromAndIdTo(@Param("id_from") Long aLong, @Param("id_to") Long aLong1);

}
