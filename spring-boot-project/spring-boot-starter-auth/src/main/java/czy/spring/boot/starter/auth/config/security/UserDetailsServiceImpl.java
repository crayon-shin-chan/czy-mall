package czy.spring.boot.starter.auth.config.security;

import czy.spring.boot.starter.auth.constant.RequestConstant;
import czy.spring.boot.starter.auth.constant.RoleConstant;
import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.entity.User;
import czy.spring.boot.starter.auth.provider.AuthorityProvider;
import czy.spring.boot.starter.auth.repository.RoleRepository;
import czy.spring.boot.starter.auth.repository.UserRepository;
import czy.spring.boot.starter.common.util.ProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link org.springframework.security.core.userdetails.UserDetailsService}实现
 * 用于获取当前{@link org.springframework.security.core.userdetails.UserDetails}
 * 在{@link DaoAuthenticationProvider}进行调用
 */
@Primary
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = false)
    private List<AuthorityProvider> authorityProviders;

    /**
     * 根据用户名加载用户{@link UserDetails}对象
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /* 获取默认角色 */
        Role defaultRole = this.roleRepository.findByIdWithAuthorities(RoleConstant.DEFAULT_ROLE_ID);

        /* 查询用户 */
        User user = this.userRepository.findUserDetails(username);

        /* 用户放入请求属性，现在还没有构造安全上下文 */
        RequestContextHolder.currentRequestAttributes().setAttribute(RequestConstant.USER,user, RequestAttributes.SCOPE_REQUEST);

        if(Objects.nonNull(defaultRole)){
            /* 添加默认角色到用户 */
            user.getRoles().add(defaultRole);
        }

        /* 从所有权限供应商获取权限 */
        Set<Authority> authorities = user.getRoles().stream()
                .filter(Objects::nonNull).filter(Role::getEnable)//过滤角色
                .flatMap(role -> role.getAuthorities().stream())//拼接所有角色的权限
                .filter(Objects::nonNull)
                .filter(Authority::getEnable)//过滤启用的权限
                .collect(Collectors.toSet());

        /* 添加provider获取的权限 */
        authorities.addAll(ProviderUtil.get(authorityProviders));

        user.setAuthorities(authorities);

        return user;
    }
}
