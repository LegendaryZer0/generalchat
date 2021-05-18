package sb.rf.generalchat.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sb.rf.generalchat.service.RefreshTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
@Slf4j
@Component
public class RefreshTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      log.info("Refresh work start work");
        if (response.getStatus() == 403) {
            Device device = DeviceUtils.getCurrentDevice(request);
            String deviceName = device.getDevicePlatform().name();

            Optional<Cookie> refreshTokenOptional = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("refresh_token")).findFirst();
            Optional<Cookie> accessToken = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("access_token")).findFirst();
            String refreshToken = objectMapper.readValue(refreshTokenOptional.orElseThrow(IllegalStateException::new).getValue(), String.class);
            Pair<String, String> tokens = refreshTokenService.getRefreshAccessTokensWithRotation(refreshToken, accessToken.orElseThrow(IllegalStateException::new).getValue(), deviceName);

            Cookie accessTokenCookie = new Cookie("access_token", tokens.getFirst());
            Cookie refreshTokenCookie = new Cookie("refresh_token", tokens.getSecond());
            addHttpOnlyCookiesToUser(response,accessTokenCookie,refreshTokenCookie);
            response.setStatus(200);
        } else filterChain.doFilter(request, response);

    }
    private void addHttpOnlyCookiesToUser(HttpServletResponse response, Cookie... cookies) {
        Arrays.stream(cookies).peek(x -> x.setHttpOnly(true)).forEach(response::addCookie);
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().contains("/api");
    }
}
