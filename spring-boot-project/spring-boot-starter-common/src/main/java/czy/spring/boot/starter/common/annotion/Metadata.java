package czy.spring.boot.starter.common.annotion;

import java.lang.annotation.*;

/**
 * 元数据权限，用户一般应该默认拥有
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Metadata {
}
