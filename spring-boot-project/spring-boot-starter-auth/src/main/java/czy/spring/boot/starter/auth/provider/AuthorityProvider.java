package czy.spring.boot.starter.auth.provider;

import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.common.interfaces.Provider;
import org.springframework.stereotype.Component;

/**
 * 权限供应商，用于不同模块进行权限提供
 */
@Component
public interface AuthorityProvider extends Provider<Authority> {



}
