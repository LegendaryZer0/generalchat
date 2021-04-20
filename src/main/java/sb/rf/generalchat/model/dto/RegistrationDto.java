package sb.rf.generalchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sb.rf.generalchat.model.TechnicalInfo;
import sb.rf.generalchat.model.User;
import sb.rf.generalchat.validation.ValidPassName;
import sb.rf.generalchat.validation.ValidPassword;


import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidPassName(message = "{errors.invalid.password.nickname}",nickname = "login",password = "password")
public class RegistrationDto{

    @Email(message = "{errors.incorrect.email}")
    private String login;
    @ValidPassword(message = "{errors.invalid.password}")
    private String password;
    private String confirm;
    public User getUser(){
        return User
                .builder()
                .email(login)
                .password(password)
                .nickname(login)
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .technicalInfo(TechnicalInfo
                        .builder()
                        .isDeleted(false)
                        .confirmState(TechnicalInfo.ConfirmState.NONE_CONFIRMED)
                        .build()).build();
    }
}