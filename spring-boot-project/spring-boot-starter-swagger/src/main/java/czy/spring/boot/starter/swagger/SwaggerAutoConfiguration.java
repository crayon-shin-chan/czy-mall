package czy.spring.boot.starter.swagger;

import czy.spring.boot.starter.common.constant.GlobalConstant;
import czy.spring.boot.starter.common.provider.ApiKeyProvider;
import czy.spring.boot.starter.common.util.ProviderUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * swagger自动配置，当czy.spring.swagger.enable=true时启用
 * {@link Import}导入了{@link DocketBeanRegistrar}用于根据配置属性动态注册{@link Docket}
 */
@EnableSwagger2
@Import(DocketBeanRegistrar.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "czy.spring.swagger", name = "enable", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Autowired(required = false)
    private List<ApiKeyProvider> apiKeyProviders;

    public SwaggerAutoConfiguration(){
    }

    /**
     * {@link Docket}Bean后处理器，用于注入{@link springfox.documentation.service.ApiInfo}
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "swaggerDocketBeanPostProcess")
    public BeanPostProcessor swaggerDocketBeanPostProcessor(){

        /* 获取所有ApiKey */
        Set<String> apiKeys = new HashSet<>();

        apiKeys.add(GlobalConstant.SESSION_HEADER);

        if(!CollectionUtils.isEmpty(apiKeyProviders)){
            apiKeys.addAll(ProviderUtil.get(apiKeyProviders));
        }

        /* Bean后处理器 */
        return new BeanPostProcessor() {

            private List<SecurityScheme> schemes;
            private List<SecurityContext> contexts;
            private List<SecurityReference> references;

            {

                AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};

                schemes = apiKeys.stream().map(k->new ApiKey(k,k, "header")).collect(Collectors.toList());

                references = apiKeys.stream().map(k->new SecurityReference(k, authorizationScopes)).collect(Collectors.toList());

                /* 上下文只有一个 */
                contexts = Collections.singletonList(SecurityContext.builder()
                        .securityReferences(references)
                        .forPaths(PathSelectors.any())
                        .build());
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(bean instanceof Docket){
                    Docket docket = (Docket)bean;
                    /** 为所有Docket注入{@link ApiInfo} */
                    docket.apiInfo(swaggerProperties.getApiInfo());
                    /** 为所有Docket注入{@link springfox.documentation.service.SecurityScheme} */
                    docket.securitySchemes(schemes);
                    docket.securityContexts(contexts);
                }
                return bean;
            }
        };
    }

    /* 默认配置 */
    @Bean
    @ConditionalOnMissingBean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                /* 扫描的包 */
                .apis(RequestHandlerSelectors.any())
                /* 请求路径过滤 */
                .paths(PathSelectors.any())
                .build();
    }

}
