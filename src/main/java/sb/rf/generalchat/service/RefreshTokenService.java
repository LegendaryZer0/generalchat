package sb.rf.generalchat.service;

import org.springframework.data.util.Pair;
import sb.rf.generalchat.model.RedisUser;
import sb.rf.generalchat.model.dto.RefreshTokenDto;

public interface RefreshTokenService {
    public Pair<String,String> getRefreshAccessTokensWithRotation(String refreshToken, String accessToken,String deviceName);

    public RedisUser addRedisUser(RedisUser redisUser);
}
