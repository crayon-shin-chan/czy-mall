package czy.spring.boot.starter.i18n;

import czy.spring.boot.starter.i18n.config.I18nSwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 应用管理模块自动配置，当czy.spring.i18n.enable=true时启用，不指定时默认启用
 */
@Configuration
@Import(I18nSwaggerConfig.class)
@ComponentScan("czy.spring.boot.starter.i18n")
@EntityScan(basePackages = "czy.spring.boot.starter.i18n.entity")
@EnableConfigurationProperties(czy.spring.boot.starter.i18n.I18nProperties.class)
@ConditionalOnProperty(prefix = "czy.spring.i18n", name = "enable", havingValue = "true", matchIfMissing = true)
public class I18nAutoConfiguration {


}
