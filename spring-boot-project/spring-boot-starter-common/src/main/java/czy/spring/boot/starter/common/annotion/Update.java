package czy.spring.boot.starter.common.annotion;

import java.lang.annotation.*;

/**
 * 更新权限
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Update {
}
