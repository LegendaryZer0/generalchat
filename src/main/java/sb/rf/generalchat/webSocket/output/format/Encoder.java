package sb.rf.generalchat.webSocket.output.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.model.dto.MessagesDto;

import javax.websocket.EndpointConfig;

@Component
@Slf4j
public class Encoder implements javax.websocket.Encoder.Text<MessagesDto> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String encode(MessagesDto object) {
    try {
      log.info("object mapper is {}", objectMapper);
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void init(EndpointConfig config) {}

  @Override
  public void destroy() {}
}
