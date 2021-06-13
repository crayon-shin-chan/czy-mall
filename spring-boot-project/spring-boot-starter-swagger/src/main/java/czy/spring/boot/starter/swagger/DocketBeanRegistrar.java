package czy.spring.boot.starter.swagger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * {@link springfox.documentation.spring.web.plugins.Docket}
 * 动态Bean注册器
 */
public class DocketBeanRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private static final String BEAN_SUFFIX = "Docket";

    /* 获取swagger属性 */
    private BeanFactory factory;

    public DocketBeanRegistrar() {
    }

    /* 动态注册Bean */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {


        SwaggerProperties swaggerProperties = factory.getBean(SwaggerProperties.class);

        if(Objects.nonNull(swaggerProperties) && !CollectionUtils.isEmpty(swaggerProperties.getDockets())){

            /** 遍历{@link DocketProperties} */
            for(DocketProperties properties:swaggerProperties.getDockets()){
                GenericBeanDefinition definition = new GenericBeanDefinition();
                definition.setBeanClass(Docket.class);

                final Docket docket = new Docket(properties.getDocumentationType())
                        .groupName(properties.getGroupName())
                        .select()
                        /* 扫描的包 */
                        .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                        /* 请求路径过滤 */
                        .paths(PathSelectors.any())
                        .build();

                /* 设置实例供应商 */
                definition.setInstanceSupplier(new Supplier<Object>() {
                    @Override
                    public Object get() {
                        return docket;
                    }
                });

                /* 注册Bean定义 */
                registry.registerBeanDefinition(properties.getGroupName()+BEAN_SUFFIX, definition);
            }

        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.factory = beanFactory;
    }
}
