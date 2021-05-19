package sb.rf.generalchat.service;

import sb.rf.generalchat.model.dto.UserLoginDto;

import javax.servlet.http.HttpServletResponse;

public interface ApiAuthService {
    String generateAccessToken(String email);

    void processUser(UserLoginDto userLoginDto, HttpServletResponse response, String deviceName);
}
