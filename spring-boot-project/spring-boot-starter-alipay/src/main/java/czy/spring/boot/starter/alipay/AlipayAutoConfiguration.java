package czy.spring.boot.starter.alipay;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 应用管理模块自动配置，当czy.spring.alipay.enable=true时启用，不指定时默认启用
 */
@Configuration
@ComponentScan("czy.spring.boot.starter.alipay")
@EnableConfigurationProperties(AlipayProperties.class)
@EntityScan(basePackages = "czy.spring.boot.starter.alipay.entity")
@ConditionalOnProperty(prefix = "czy.spring.alipay", name = "enable", havingValue = "true", matchIfMissing = true)
public class AlipayAutoConfiguration {



}
