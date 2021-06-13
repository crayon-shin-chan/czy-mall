package czy.spring.boot.starter.auth;


import czy.spring.boot.starter.auth.config.AuthSwaggerConfig;
import czy.spring.boot.starter.auth.config.SpringMethodSecurityConfig;
import czy.spring.boot.starter.auth.config.SpringSecurityConfig;
import czy.spring.boot.starter.auth.config.SpringSessionConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 认证模块自动配置，当czy.spring.auth.enable=true时启用，不指定时默认启用
 */
@Configuration
@Import({
        AuthSwaggerConfig.class,
        SpringSessionConfig.class,
        SpringSecurityConfig.class,
        SpringMethodSecurityConfig.class
})
@ComponentScan("czy.spring.boot.starter.auth")
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
@EnableConfigurationProperties(AuthProperties.class)
@EntityScan(basePackages = "czy.spring.boot.starter.auth.entity")
@ConditionalOnProperty(prefix = "czy.spring.auth", name = "enable", havingValue = "true", matchIfMissing = true)
public class AuthAutoConfiguration {

    private final AuthProperties authProperties;

    public AuthAutoConfiguration(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

}
