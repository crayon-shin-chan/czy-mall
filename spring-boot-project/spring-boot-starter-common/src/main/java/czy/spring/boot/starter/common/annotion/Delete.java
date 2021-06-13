package czy.spring.boot.starter.common.annotion;

import java.lang.annotation.*;

/**
 * 删除权限
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Delete {
}
