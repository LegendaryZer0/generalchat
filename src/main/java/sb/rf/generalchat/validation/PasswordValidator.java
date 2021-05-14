package sb.rf.generalchat.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {
    private static final Pattern minEightCharsAtLeastOneLetterOneNumber =Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("Valiue on password validator {}", value);
        return  minEightCharsAtLeastOneLetterOneNumber.matcher(value).matches();
    }
}
