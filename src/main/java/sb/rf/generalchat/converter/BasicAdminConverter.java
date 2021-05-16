package sb.rf.generalchat.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sb.rf.generalchat.model.TechnicalInfo;
import sb.rf.generalchat.model.User;
@Component
public class BasicAdminConverter implements Converter<String, User> {

    @Override
    public User convert(String source) {
        return User.builder().nickname(source).email(source).password("assasinnyx1234")
                .role(User.Role.ADMIN)
                .state(User.State.ACTIVE)
                .technicalInfo(TechnicalInfo.builder()
                        .isDeleted(false)
                        .confirmState(TechnicalInfo.ConfirmState.CONFIRMED)
                        .build()).build();
    }
}
