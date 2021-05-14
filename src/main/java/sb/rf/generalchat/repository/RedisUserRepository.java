package sb.rf.generalchat.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sb.rf.generalchat.model.RedisUser;

import java.util.Map;
import java.util.Optional;

public interface RedisUserRepository extends KeyValueRepository<RedisUser,String> {
    public Optional<RedisUser> findRedisUserByUserId(Long userId);

    public Optional<RedisUser> findRedisUserByDeviceNameAndUserId(String deviceName,Long userId);

    public void deleteRedisUserByDeviceNameAndUserId(String deviceName,Long userId);



}
