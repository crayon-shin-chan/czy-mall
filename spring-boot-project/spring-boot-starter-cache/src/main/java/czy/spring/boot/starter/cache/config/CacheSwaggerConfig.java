package czy.spring.boot.starter.cache.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class CacheSwaggerConfig {

    private static final String MODULE_MAME = "cache-module";

    @Bean
    public Docket cacheSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.cache"))
                .paths(PathSelectors.any())
                .build();
    }

}
