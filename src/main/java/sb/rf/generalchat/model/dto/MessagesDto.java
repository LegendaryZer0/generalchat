package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sb.rf.generalchat.model.Message;


import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessagesDto {
    private Long from;
    private Long to;
    private String message;
    private Timestamp time;
    private MessageType type;
    public enum MessageType{
        DOCUMENT,STRING
    }

    public static MessagesDto from(Message message){
        return MessagesDto.builder().from(message.getIdFrom()).to(message.getIdTo()).message(message.getMessage()).build();
    }
    public static List<MessagesDto> from(List<Message> messages){
      return   messages.stream().map(MessagesDto::from).collect(Collectors.toList());
    }
    public Message converToMessage(){
        return Message.builder().message(message).idFrom(from).idTo(to).time(time).build();
    }
}
