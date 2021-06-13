package czy.spring.boot.starter.common.validate.constraint;

import czy.spring.boot.starter.common.validate.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 邮箱验证器注解
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Target({ FIELD, METHOD, PARAMETER })
public @interface Email  {

    /* 异常消息 */
    String message() default "{czy.spring.boot.common.starter.validator.constraint.Email.message}";

    /* 分组 */
    Class<?>[] groups() default { };

    /* 载荷 */
    Class<? extends Payload>[] payload() default { };

}
