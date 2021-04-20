package sb.rf.generalchat.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("Valiue on password validator {}", value);
        return value.length()>7 && value.matches(".*[a-z].*") && value.matches(".*[1-9].*");
    }
}
