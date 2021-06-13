package czy.spring.boot.starter.admin.action.impl;

import czy.spring.boot.starter.admin.constant.SessionConstant;
import czy.spring.boot.starter.auth.action.LoginAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Order(3)
@Component
public class ApplicationLoginAction implements LoginAction {

    @Autowired
    private HttpServletRequest request;

    @Override
    public String getName() {
        return "application";
    }

    @Override
    public Object doAction() {
        return request.getSession().getAttribute(SessionConstant.APPLICATION);
    }

}
