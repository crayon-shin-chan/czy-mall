package czy.spring.boot.starter.captcha;

import czy.spring.boot.starter.captcha.config.CaptchaSwaggerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 验证码自动配置，当czy.spring.captcha.enable=true时启用，不指定时默认启用
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfiguration {

    @Import({CaptchaSwaggerConfig.class})
    @ComponentScan({"czy.spring.boot.starter.captcha.controller","czy.spring.boot.starter.captcha.service"})
    @ConditionalOnProperty(prefix = "czy.spring.captcha", name = "enable", havingValue = "true", matchIfMissing = true)
    static class EnableCaptchaConfiguration{

    }

    /**
     * 禁用验证码的配置，禁用时扫描Service
     */
    @ComponentScan("czy.spring.boot.starter.captcha.service")
    @ConditionalOnProperty(prefix = "czy.spring.captcha", name = "enable", havingValue = "false", matchIfMissing = false)
    static class DisableCaptchaConfiguration{

    }

}
