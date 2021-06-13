package czy.spring.boot.starter.captcha.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class CaptchaSwaggerConfig {

    private static final String MODULE_MAME = "captcha-module";

    @Bean
    public Docket captcheSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.captcha"))
                .paths(PathSelectors.any())
                .build();
    }

}
