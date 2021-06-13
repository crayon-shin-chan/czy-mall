package czy.spring.boot.starter.common;

import czy.spring.boot.starter.common.config.JacksonConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 通用自动配置类
 */
@Configuration
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@Import({JacksonConfig.class})
@ComponentScan("czy.spring.boot.starter.common")
public class CommonAutoConfiguration {


}
