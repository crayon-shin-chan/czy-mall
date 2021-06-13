package czy.spring.boot.starter.common.config;

import czy.spring.boot.starter.common.config.jackson.DefaultJackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/* jackson配置 */
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultJackson2ObjectMapperBuilderCustomizer(){
        return new DefaultJackson2ObjectMapperBuilderCustomizer();
    }

}
