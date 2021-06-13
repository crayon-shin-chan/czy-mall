package czy.spring.boot.starter.auth.action.impl;

import czy.spring.boot.starter.auth.action.LoginAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户token的动作
 */
@Order(0)
@Component
public class TokenLoginAction implements LoginAction {

    @Autowired
    private HttpServletRequest request;

    @Override
    public String getName() {
        return "token";
    }

    @Override
    public Object doAction() {
        return request.getSession().getId();
    }
}
