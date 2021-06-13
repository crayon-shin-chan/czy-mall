package czy.spring.boot.starter.auth.config.audit;

import czy.spring.boot.starter.auth.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("SpringSecurityAuditorAware")
class SpringSecurityAuditorAware implements AuditorAware<Long> {

    public Optional<Long> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext())//获取安全上下文
                .map(SecurityContext::getAuthentication)//获取认证对象
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)//获取认证主体
                .map(User.class::cast)//转换为用户
                .map(User::getId);//返回ID
    }
}