package czy.spring.boot.starter.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA自动配置
 * 启用JPA仓库
 * 启用JPA审计功能，审计供应商Bean在认证模块配置
 */
@Configuration
@EnableJpaRepositories(basePackages = "czy.spring.boot.starter")
@EnableJpaAuditing(auditorAwareRef = "SpringSecurityAuditorAware")
public class JpaAutoConfiguration {
}
