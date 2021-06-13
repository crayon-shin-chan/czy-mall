package czy.spring.boot.starter.admin.action.impl;

import czy.spring.boot.starter.admin.entity.Application;
import czy.spring.boot.starter.admin.entity.Menu;
import czy.spring.boot.starter.admin.ienum.ApplicationType;
import czy.spring.boot.starter.admin.ienum.MenuDisplayMode;
import czy.spring.boot.starter.admin.repository.ApplicationRepository;
import czy.spring.boot.starter.admin.repository.MenuRepository;
import czy.spring.boot.starter.admin.service.ApplicationService;
import czy.spring.boot.starter.admin.service.MenuService;
import czy.spring.boot.starter.common.interfaces.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 菜单初始化动作
 */
@Slf4j
@Order(3)
@Component
public class MenuInitAction implements InitAction {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public String getName() {
        return "菜单初始化";
    }

    @Override
    public Object doAction() {

        Application application = new Application("管理后台", ApplicationType.WEB_PC,"1000","1234567890",null,null,true,null);
        application.setId(1L);
        if(!this.applicationRepository.existsById(1L)){
            this.applicationRepository.save(application);
        }

        /* 系统管理 */
        Menu system = new Menu("system","/system","系统管理","layouts/RouteView","setting",null,false,1,1, MenuDisplayMode.HAVE_PERMISSION,null,true,application,null,null);
        system.setId(1L);
        if(!this.menuRepository.existsById(1L)){
            this.menuRepository.save(system);
        }

        /* 用户管理 */
        Menu user = new Menu("system-user","/system/user","用户管理","views/system/user/UserList","setting",null,false,2,1, MenuDisplayMode.ALLAYS,null,true,application,system,null);
        user.setId(2L);
        if(!this.menuRepository.existsById(2L)){
            this.menuRepository.save(user);
        }

        /* 角色管理 */
        Menu role = new Menu("system-role","/system/role","角色管理","views/system/role/RoleList","setting",null,false,2,2, MenuDisplayMode.ALLAYS,null,true,application,system,null);
        role.setId(3L);
        if(!this.menuRepository.existsById(3L)){
            this.menuRepository.save(role);
        }

        /* 权限管理 */
        Menu permission = new Menu("system-permission","/system/permission","权限管理","views/system/permission/PermissionList","setting",null,false,2,3, MenuDisplayMode.ALLAYS,null,true,application,system,null);
        permission.setId(4L);
        if(!this.menuRepository.existsById(4L)){
            this.menuRepository.save(permission);
        }

        /* 应用管理 */
        Menu applicationManage = new Menu("system-application","/system/application","应用管理","views/system/application/ApplicationList","setting",null,false,2,4, MenuDisplayMode.ALLAYS,null,true,application,system,null);
        applicationManage.setId(5L);
        if(!this.menuRepository.existsById(5L)){
            this.menuRepository.save(applicationManage);
        }

        /* 菜单管理 */
        Menu menu = new Menu("system-menu","/system/menu","菜单管理","views/system/menu/MenuList","setting",null,false,2,5, MenuDisplayMode.ALLAYS,null,true,application,system,null);
        menu.setId(6L);
        if(!this.menuRepository.existsById(6L)){
            this.menuRepository.save(menu);
        }

        this.menuService.updateCache();
        this.applicationService.updateCache();

        return null;
    }


}
