package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sb.rf.generalchat.model.User;


import java.util.List;
import java.util.Optional;

public  interface UserJpaRepository extends JpaRepository<User,Long> {
    @Transactional
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    @Transactional
    @Query(nativeQuery = true, value = "select * from account where account.id in(select id_to from chats where id_from=:user_id or id_to =:user_id union  select id_from  from chats where id_from=:user_id or id_to =:user_id)")
    List<User> findAllUserChatsById(@Param("user_id") long id);



    @Transactional
    @Query("update User u set u =:account where u.id=:id")
    @Modifying
    void updateUser(@Param("account") User user,@Param("id")Long id);
    @Transactional
    @Query("update TechnicalInfo set confirmState =:state where id=:id")
    @Modifying
    void confirmUser(@Param("id") long id,@Param("state") String state);

    @Transactional
    List<User> getAllByTechnicalInfo_IsDeletedFalse();
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update technical_info  set isdeleted=true where technical_info.id in(select id from account where account.email=:email) ")
    Integer markUserAsDeleted(@Param("email") String email);




}
