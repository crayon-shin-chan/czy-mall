package czy.spring.boot.starter.auth.action.impl;

import czy.spring.boot.starter.auth.action.LoginAction;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 获取用户详情的登录动作
 */
@Order(1)
@Component
public class UserDetailsLoginAction implements LoginAction {

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public Object doAction() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
