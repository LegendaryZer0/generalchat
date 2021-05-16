package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.validation.ValidPassName;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidPassName(
        message = "{errors.invalid.password.nickname}",
        nickname = "nickname",
        password = "password"
)

public class UserChangeSettingsDto {

    private Long id;

    private String email;

    private String password;


    private String phone;

    private String nickname;
    public User getUser(){
        return User.builder().id(id).email(email).password(password).nickname(nickname).phone(phone).build();
    }
    public static UserChangeSettingsDto from(User user){
        return UserChangeSettingsDto
                .builder().id(user.getId()).email(user.getEmail())
                .password(user.getPassword()).nickname(user.getNickname()).phone(user.getPhone()).build();
    }
}
