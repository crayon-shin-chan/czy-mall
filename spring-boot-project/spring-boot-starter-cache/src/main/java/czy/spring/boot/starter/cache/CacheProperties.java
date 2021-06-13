package czy.spring.boot.starter.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 管理模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.cache")
public class CacheProperties {

    /* 是否启用管理模块 */
    private boolean enable = true;

}
