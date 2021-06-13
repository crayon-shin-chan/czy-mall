package czy.spring.boot.starter.i18n.action;

import czy.spring.boot.starter.auth.action.LoginAction;
import czy.spring.boot.starter.i18n.service.I18nItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 国际化登陆动作，返回国际化信息
 */
@Order(6)
@Component
public class I18nLoginAction implements LoginAction {

    @Autowired
    private I18nItemService i18nItemService;

    @Override
    public String getName() {
        return "i18n";
    }

    @Override
    public Object doAction() {
        return this.i18nItemService.getLangCodeMap();
    }

}
