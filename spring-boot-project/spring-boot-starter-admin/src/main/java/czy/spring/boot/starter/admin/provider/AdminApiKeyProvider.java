package czy.spring.boot.starter.admin.provider;

import czy.spring.boot.starter.admin.constant.ApplicationConstant;
import czy.spring.boot.starter.common.provider.ApiKeyProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ApiKey供应商
 */
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AdminApiKeyProvider implements ApiKeyProvider {

    @Override
    public Set<String> get() {
        return new HashSet<>(Arrays.asList(ApplicationConstant.APP_ID,ApplicationConstant.APP_SECRET));
    }

}
