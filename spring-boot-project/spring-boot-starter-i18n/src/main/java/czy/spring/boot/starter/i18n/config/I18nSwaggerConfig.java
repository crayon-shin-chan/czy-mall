package czy.spring.boot.starter.i18n.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class I18nSwaggerConfig {

    private static final String MODULE_MAME = "i18n-module";

    /* Bean名称最好不一样 */
    @Bean
    public Docket i18nSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.i18n"))
                .paths(PathSelectors.any())
                .build();
    }

}
