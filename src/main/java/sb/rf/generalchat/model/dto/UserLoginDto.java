package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sb.rf.generalchat.model.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDto {

    private String password;
    private String login;
    private Boolean isChecked;

    public User getUser(){
        return User.builder().password(password).email(login).build();
    }
}
