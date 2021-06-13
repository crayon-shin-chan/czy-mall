package czy.spring.boot.starter.admin.action.impl;

import czy.spring.boot.starter.admin.constant.SessionConstant;
import czy.spring.boot.starter.admin.entity.Application;
import czy.spring.boot.starter.admin.entity.Menu;
import czy.spring.boot.starter.admin.repository.MenuRepository;
import czy.spring.boot.starter.admin.service.MenuService;
import czy.spring.boot.starter.auth.action.LoginAction;
import czy.spring.boot.starter.jpa.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取菜单的登录动作
 */
@Order(2)
@Component
public class MenuLoginAction implements LoginAction {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Override
    public String getName() {
        return SessionConstant.MENU;
    }

    @Override
    public Object doAction() {

        /* 获取应用对象，获取应用的菜单 */
        Application application = (Application) request.getSession().getAttribute(SessionConstant.APPLICATION);
        if(Objects.isNull(application)){
            return new ArrayList<>();
        }
        List<Menu> menus = this.menuService.getCache().values().stream().filter(m->m.getApplication().getId().equals(application.getId())).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(menus)){
            return new ArrayList<>();
        }

        /* 菜单变为树 */
        List<Menu> tree =  TreeUtil.tree(menus);

        request.getSession().setAttribute(SessionConstant.MENU,tree);

        return tree;
    }
}
