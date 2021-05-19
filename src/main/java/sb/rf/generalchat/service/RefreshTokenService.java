package sb.rf.generalchat.service;

import org.springframework.data.util.Pair;
import sb.rf.generalchat.model.RedisUser;

public interface RefreshTokenService {
    Pair<String, String> getRefreshAccessTokensWithRotation(String refreshToken, String accessToken, String deviceName);

    RedisUser addRedisUser(RedisUser redisUser);
}
