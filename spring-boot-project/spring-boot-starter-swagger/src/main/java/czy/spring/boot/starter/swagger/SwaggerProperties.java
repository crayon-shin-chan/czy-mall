package czy.spring.boot.starter.swagger;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * swagger配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.swagger")
public class SwaggerProperties implements InitializingBean{

    /* 是否启用swagger配置 */
    private boolean enable = true;

    /** API信息 */
    private ApiInfo apiInfo = ApiInfo.DEFAULT;

    /** 配置文件中多个{@link Docket}的属性 */
    private List<DocketProperties> dockets;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(apiInfo,"ApiInfo can not be null");
        Assert.notNull(apiInfo.getContact(),"ApiInfo.Contact can not be null");
    }
}
