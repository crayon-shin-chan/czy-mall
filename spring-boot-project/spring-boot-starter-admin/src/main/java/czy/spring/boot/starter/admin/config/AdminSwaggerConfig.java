package czy.spring.boot.starter.admin.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class AdminSwaggerConfig {

    private static final String MODULE_MAME = "admin-module";

    /* Bean名称最好不一样 */
    @Bean
    public Docket adminSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.admin"))
                .paths(PathSelectors.any())
                .build();
    }

}
