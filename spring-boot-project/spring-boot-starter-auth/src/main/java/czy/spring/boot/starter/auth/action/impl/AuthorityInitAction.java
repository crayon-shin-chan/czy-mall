package czy.spring.boot.starter.auth.action.impl;

import czy.spring.boot.starter.auth.AuthProperties;
import czy.spring.boot.starter.auth.config.security.PermissionMethodSecurityMetadataSource;
import czy.spring.boot.starter.auth.constant.UserConstant;
import czy.spring.boot.starter.auth.service.AuthorityService;
import czy.spring.boot.starter.auth.util.RunAsUtil;
import czy.spring.boot.starter.common.interfaces.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 权限扫描初始化
 */
@Slf4j
@Order(0)
@Component
public class AuthorityInitAction implements InitAction {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public String getName() {
        return "权限初始化";
    }

    @Override
    public Object doAction(){

        RunAsUtil.runAs(UserConstant.ADMIN_USER_ID,authProperties.getAdmin().getUsername());

        if(authProperties.isPermissionSync()){
            /* 可以保证到这里时权限扫描已经完成 */
            this.authorityService.permissionSync(PermissionMethodSecurityMetadataSource.getAuthorities());
        }
        return null;
    }
}
