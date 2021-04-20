package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sb.rf.generalchat.model.Chats;


import java.util.UUID;

public interface ChatsRepository extends JpaRepository<Chats,Long> {


    @Query("select chat.uuid from Chats  chat  where  ((chat.idFrom=:id_from and  chat.idTo=:id_to) or (chat.idFrom =:id_to and chat.idTo =:id_from)) " //Todo дописать or ? (разобраться с костылём)
    )
    UUID getChatroomUUIDForTwoUsers(@Param("id_from")Long aLong,@Param("id_to") Long aLong1);


  /*  @Query( "insert  into  Chats values (id_from)")
    UUID createChatroom(@Param("id_from") Long id_from,@Param("id_to") Long id_to);*/
}
