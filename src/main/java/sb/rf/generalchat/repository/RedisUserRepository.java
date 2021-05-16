package sb.rf.generalchat.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import sb.rf.generalchat.model.RedisUser;

import java.util.Optional;

public interface RedisUserRepository extends KeyValueRepository<RedisUser, String> {
    Optional<RedisUser> findRedisUserByUserId(Long userId);

    Optional<RedisUser> findRedisUserByDeviceNameAndUserId(String deviceName, Long userId);

    void deleteRedisUserByDeviceNameAndUserId(String deviceName, Long userId);


}
