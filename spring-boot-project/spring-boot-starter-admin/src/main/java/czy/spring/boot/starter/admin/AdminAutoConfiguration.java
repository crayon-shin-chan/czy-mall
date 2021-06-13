package czy.spring.boot.starter.admin;

import czy.spring.boot.starter.admin.config.AdminFilterConfig;
import czy.spring.boot.starter.admin.config.AdminSwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 应用管理模块自动配置，当czy.spring.admin.enable=true时启用，不指定时默认启用
 */
@ComponentScan("czy.spring.boot.starter.admin")
@EnableConfigurationProperties(AdminProperties.class)
@Import({AdminSwaggerConfig.class, AdminFilterConfig.class})
@EntityScan(basePackages = "czy.spring.boot.starter.admin.entity")
@ConditionalOnProperty(prefix = "czy.spring.admin", name = "enable", havingValue = "true", matchIfMissing = true)
public class AdminAutoConfiguration {



}
