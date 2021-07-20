package sb.rf.generalchat.webSocket.output.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.model.dto.MessagesDto;

import javax.websocket.EndpointConfig;

@Slf4j
@Component
public class Decoder implements javax.websocket.Decoder.Text<MessagesDto> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void init(EndpointConfig config) {}

  @Override
  public void destroy() {}

  @Override
  public MessagesDto decode(String s) {
    try {
      return objectMapper.readValue(s, MessagesDto.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public boolean willDecode(String s) {
    log.info("Пробую раскодировать сообщение");
    try {
      log.info("object mapper is {}", objectMapper);
      objectMapper.readValue(s, MessagesDto.class);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
