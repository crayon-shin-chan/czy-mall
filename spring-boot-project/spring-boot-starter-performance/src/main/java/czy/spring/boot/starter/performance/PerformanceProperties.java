package czy.spring.boot.starter.performance;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 性能监控模块配置熟悉
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.performance")
public class PerformanceProperties {

    /* 是否启用性能模块，默认false不启用 */
    private boolean enable = false;

}
