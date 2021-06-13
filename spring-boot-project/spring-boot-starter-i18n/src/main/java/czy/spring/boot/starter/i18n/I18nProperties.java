package czy.spring.boot.starter.i18n;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 管理模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.i18n")
public class I18nProperties {

    /* 是否启用国际化模块 */
    private boolean enable = true;

}
