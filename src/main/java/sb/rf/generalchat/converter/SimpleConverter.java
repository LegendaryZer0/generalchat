package sb.rf.generalchat.converter;

import org.springframework.core.convert.converter.Converter;
import sb.rf.generalchat.model.User;

public class SimpleConverter implements Converter<String, User> {

    @Override
    public User convert(String source) {
        return User.builder().nickname(source).build();
    }
}
