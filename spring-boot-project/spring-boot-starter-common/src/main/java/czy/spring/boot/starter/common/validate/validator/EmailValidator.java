package czy.spring.boot.starter.common.validate.validator;

import czy.spring.boot.starter.common.util.ValidateUtil;
import czy.spring.boot.starter.common.validate.constraint.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 邮箱验证器
 */
public class EmailValidator implements ConstraintValidator<Email,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidateUtil.isEmail(value);
    }
}
