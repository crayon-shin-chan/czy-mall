package czy.spring.boot.starter.mall;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import czy.spring.boot.starter.mall.MallProperties;

/**
 * 应用管理模块自动配置，当czy.spring.admin.enable=true时启用，不指定时默认启用
 */
@Configuration
@ComponentScan("czy.spring.boot.starter.mall")
@EnableConfigurationProperties(MallProperties.class)
@EntityScan(basePackages = "czy.spring.boot.starter.mall.entity")
@ConditionalOnProperty(prefix = "czy.spring.mall", name = "enable", havingValue = "true", matchIfMissing = true)
public class MallAutoConfiguration {

}
