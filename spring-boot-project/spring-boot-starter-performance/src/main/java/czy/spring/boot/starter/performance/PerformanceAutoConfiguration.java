package czy.spring.boot.starter.performance;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 性能监控模块
 */
@EnableAspectJAutoProxy
@ComponentScan("czy.spring.boot.starter.performance")
@EnableConfigurationProperties(PerformanceProperties.class)
@EntityScan(basePackages = "czy.spring.boot.starter.performance")
@ConditionalOnProperty(prefix = "czy.spring.performance", name = "enable", havingValue = "true", matchIfMissing = false)
public class PerformanceAutoConfiguration {
}
