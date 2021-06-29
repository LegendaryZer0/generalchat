package sb.rf.generalchat.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NamesValidator implements ConstraintValidator<ValidPassName, Object> {
  private String nicknamePropertyName;
  private String passwordPropertyName;

  @Override
  public void initialize(ValidPassName constraintAnnotation) {
    this.nicknamePropertyName =
        constraintAnnotation.nickname(); // название поля для name -> firstName
    this.passwordPropertyName =
        constraintAnnotation.password(); // название поля для surname -> lastName
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Object name =
        new BeanWrapperImpl(value)
            .getPropertyValue(nicknamePropertyName); // получили конкретные значения
    Object surname = new BeanWrapperImpl(value).getPropertyValue(passwordPropertyName);

    return name != null && !name.equals(surname);
  }
}
