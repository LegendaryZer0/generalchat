package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sb.rf.generalchat.model.User;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FirstMessageDto {
    @Email(message = "{errors.incorrect.email}")
    private String toEmail;
    @Size(min =1 ,message = "{errors.empty.message}")
    private String message;
    public User getUser(){
        return User.builder().email(toEmail).build();
    }
}
