package czy.spring.boot.starter.auth.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class AuthSwaggerConfig {

    private static final String AUTH_MODULE_MAME = "auth-module";
    private static final String USER_MODULE_MAME = "user-module";
    private static final String ROLE_MODULE_MAME = "role-module";
    private static final String AUTHORITY_MODULE_MAME = "authority-module";

    /* 认证模块 */
    @Bean
    public Docket authSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(AUTH_MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.auth.controller"))
                .paths(PathSelectors.ant("/auth/**"))
                .build();
    }

    /* 用户管理模块 */
    @Bean
    public Docket userSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(USER_MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.auth.controller"))
                .paths(PathSelectors.ant("/user/**"))
                .build();
    }

    /* 角色管理模块 */
    @Bean
    public Docket roleSwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(ROLE_MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.auth.controller"))
                .paths(PathSelectors.ant("/role/**"))
                .build();
    }

    
    /* 权限管理模块 */
    @Bean
    public Docket authoritySwaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(AUTHORITY_MODULE_MAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage("czy.spring.boot.starter.auth.controller"))
                .paths(PathSelectors.ant("/authority/**"))
                .build();
    }

}
