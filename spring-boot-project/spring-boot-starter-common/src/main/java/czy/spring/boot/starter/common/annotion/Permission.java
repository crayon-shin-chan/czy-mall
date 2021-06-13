package czy.spring.boot.starter.common.annotion;

import java.lang.annotation.*;

/**
 * 权限注解，用于在类与方法上指定权限
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Permission {

    /**
     * 访问方法、类需要的权限，之间为或的关系，目前只支持一个接口一个权限
     */
    String value();

}
