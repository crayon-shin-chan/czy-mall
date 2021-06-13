package czy.spring.boot.starter.common.config.jackson;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * 默认jackson obejct mapper定制化器，用于定制化自动配置的{@link com.fasterxml.jackson.databind.ObjectMapper}
 */
public class DefaultJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        /* jackson日期格式化 */
        builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                /* 是service loader获取Module */
                .findModulesViaServiceLoader(true);
    }
}
