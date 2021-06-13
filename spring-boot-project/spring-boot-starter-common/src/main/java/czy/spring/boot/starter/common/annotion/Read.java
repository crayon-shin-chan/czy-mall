package czy.spring.boot.starter.common.annotion;

import java.lang.annotation.*;

/**
 * 读取权限
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Read {
}
