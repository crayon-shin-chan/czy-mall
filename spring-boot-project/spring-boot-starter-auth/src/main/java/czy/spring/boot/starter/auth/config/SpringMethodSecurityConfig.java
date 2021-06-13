package czy.spring.boot.starter.auth.config;

import czy.spring.boot.starter.auth.config.security.PermissionMethodSecurityMetadataSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

/**
 * spring方法安全配置，需要继承{@link GlobalMethodSecurityConfiguration}，因为这个里面的bean不能覆盖
 * 只能通过继承跳过原配置，使用自定义配置，可以查看
 * 这个Bean的角色应该是{@link BeanDefinition#ROLE_INFRASTRUCTURE}
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
public class SpringMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected RunAsManager runAsManager() {
        return new RunAsManagerImpl();
    }

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new PermissionMethodSecurityMetadataSource();
    }

    /* 设置默认角色前缀 */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }
}
