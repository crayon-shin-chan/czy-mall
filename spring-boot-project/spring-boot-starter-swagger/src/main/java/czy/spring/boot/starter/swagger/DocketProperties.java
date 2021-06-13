package czy.spring.boot.starter.swagger;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import springfox.documentation.spi.DocumentationType;

/**
 * {@link springfox.documentation.spring.web.plugins.Docket}属性
 */
@Data
public class DocketProperties implements InitializingBean {

    /** 文档类型，默认为{@link DocumentationType#SWAGGER_2} */
    private DocumentationType documentationType = DocumentationType.SWAGGER_2;

    /** 分组名称 */
    private String groupName;

    /** 扫描的基础包 */
    private String basePackage = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(groupName,"groupName can not be nul");
        Assert.notNull(basePackage,"basePackage can not be null");
    }

}
