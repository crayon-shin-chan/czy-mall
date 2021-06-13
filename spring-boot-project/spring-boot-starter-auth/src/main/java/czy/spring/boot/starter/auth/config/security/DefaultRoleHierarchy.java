package czy.spring.boot.starter.auth.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限继承计算组件
 */
@Slf4j
@Component
public class DefaultRoleHierarchy implements RoleHierarchy {

    /**
     * 根据已有权限计算可达权限
     * @param authorities
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        /* 当前实现返回原有权限 */
        return authorities;
    }
}
