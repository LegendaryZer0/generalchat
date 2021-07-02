package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sb.rf.generalchat.model.Message;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class MessagesDto {
  private Long from;
  private Long to;
  private String message;
  private Timestamp time;
  private MessageType type;

  public static MessagesDto from(Message message) {
    log.info("messaageDTO from message {}",message);
    return MessagesDto.builder()
        .from(message.getIdFrom())
        .to(message.getIdTo())
            .type(MessageType.valueOf(message.getMessageType().name()))
        .message(message.getMessage())
        .build();
  }

  public static List<MessagesDto> from(List<Message> messages) {
    return messages.stream().map(MessagesDto::from).collect(Collectors.toList());
  }

  public Message converToMessage() {
    return Message.builder().message(message).messageType(Message.MessageType.valueOf(type.name())).idFrom(from).idTo(to).time(time).build();
  }

  public enum MessageType {
    DOCUMENT,
    STRING
  }
}
