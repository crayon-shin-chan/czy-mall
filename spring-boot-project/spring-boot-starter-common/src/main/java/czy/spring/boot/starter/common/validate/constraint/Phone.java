package czy.spring.boot.starter.common.validate.constraint;

import czy.spring.boot.starter.common.validate.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号码验证注解，被此注解注解的字段，会被{@link PhoneValidator}所验证
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Target({ FIELD, METHOD, PARAMETER })
public @interface Phone {

    /* 异常消息 */
    String message() default "{czy.spring.boot.common.starter.validator.constraint.Phone.message}";

    /* 分组 */
    Class<?>[] groups() default { };

    /* 载荷 */
    Class<? extends Payload>[] payload() default { };

}
