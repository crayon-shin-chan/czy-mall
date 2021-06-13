package czy.spring.boot.starter.mall;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 管理模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.mall")
public class MallProperties {

    /* 是否启用管理模块 */
    private boolean enable = true;

}
