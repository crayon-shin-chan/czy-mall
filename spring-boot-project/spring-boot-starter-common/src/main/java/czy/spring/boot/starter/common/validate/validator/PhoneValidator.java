package czy.spring.boot.starter.common.validate.validator;

import czy.spring.boot.starter.common.util.ValidateUtil;
import czy.spring.boot.starter.common.validate.constraint.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码验证器
 */
public class PhoneValidator implements ConstraintValidator<Phone,String> {

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ValidateUtil.isPhone(value);
    }

}
