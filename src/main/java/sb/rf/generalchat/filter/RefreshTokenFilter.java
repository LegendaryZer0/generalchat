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

@Slf4j
@Component
public class RefreshTokenFilter extends OncePerRequestFilter {

  @Autowired private RefreshTokenService refreshTokenService;

  @Autowired private ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("refresh token was invoked");
    if (response.getStatus() == 403) {
      log.info("It seems like access token is expired");
      Device device = DeviceUtils.getCurrentDevice(request);
      log.info("device {}", device);
      String deviceName = device.getDevicePlatform().name();

      Optional<Cookie> refreshTokenOptional =
          Arrays.stream(request.getCookies())
              .filter(x -> x.getName().equals("refresh_token"))
              .findFirst();
      Optional<Cookie> accessToken =
          Arrays.stream(request.getCookies())
              .filter(x -> x.getName().equals("access_token"))
              .findFirst();
      Arrays.stream(request.getCookies())
          .forEach(x -> log.info("cookie name {} and value {} ", x.getName(), x.getValue()));
      String refreshToken = refreshTokenOptional.orElseThrow(IllegalStateException::new).getValue();
      log.info("refresh token is {}", refreshToken);
      log.info("expired access token {}", accessToken.get().getValue());

      Pair<String, String> tokens =
          refreshTokenService.getRefreshAccessTokensWithRotation(
              refreshToken, accessToken.get().getValue(), deviceName);
      Cookie accessTokenCookie = new Cookie("access_token", tokens.getFirst());
      Cookie refreshTokenCookie = new Cookie("refresh_token", tokens.getSecond());
      addHttpOnlyCookiesToUser(response, accessTokenCookie, refreshTokenCookie);

      response.setStatus(200);
    }
    filterChain.doFilter(request, response);
  }

  private void addHttpOnlyCookiesToUser(HttpServletResponse response, Cookie... cookies) {
    Arrays.stream(cookies).peek(x -> x.setHttpOnly(true)).forEach(response::addCookie);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return (request.getRequestURI().contains("/v2/api-docs") ||!(request.getRequestURI().contains("/api")));
  }
}
