package czy.spring.boot.starter.cache;

import czy.spring.boot.starter.cache.config.CacheConfig;
import czy.spring.boot.starter.cache.config.CacheSwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 应用管理模块自动配置，当czy.spring.cache.enable=true时启用，不指定时默认启用
 */
@EnableCaching
@Import({CacheSwaggerConfig.class, CacheConfig.class})
@ComponentScan("czy.spring.boot.starter.cache")
@EnableConfigurationProperties(CacheProperties.class)
@EntityScan(basePackages = "czy.spring.boot.starter.cache.entity")
@ConditionalOnProperty(prefix = "czy.spring.cache", name = "enable", havingValue = "true", matchIfMissing = true)
public class CacheAutoConfiguration {

}
