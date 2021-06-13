package czy.spring.boot.starter.auth.config.security;

import czy.spring.boot.starter.common.constant.GlobalConstant;
import czy.spring.boot.starter.common.provider.ApiKeyProvider;
import czy.spring.boot.starter.common.util.ProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* 跨域配置源，security会使用这里的属性配置跨域过滤器
 * 安全框架查找跨域配置的Bean name为corsConfigurationSource
 */
@Component("corsConfigurationSource")
public class DefaultCorsConfigurationSource implements CorsConfigurationSource {

    @Autowired(required = false)
    private List<ApiKeyProvider> apiKeyProviders;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

        /* 获取所有ApiKey */
        Set<String> apiKeys = new HashSet<>();

        apiKeys.add(GlobalConstant.SESSION_HEADER);

        if(!CollectionUtils.isEmpty(apiKeyProviders)){
            apiKeys.addAll(ProviderUtil.get(apiKeyProviders));
        }

        CorsConfiguration configuration = new CorsConfiguration();
        /* 请求cookie */
        configuration.setAllowCredentials(true);
        /* 请求header、方法、源 */
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        /* 浏览器可以访问的响应头 */
        for(String header:apiKeys){
            configuration.addExposedHeader(header);
        }
        configuration.setMaxAge(3600*24*10L);
        return configuration;
    }

}


