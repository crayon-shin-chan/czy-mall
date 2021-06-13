package czy.spring.boot.starter.auth;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 认证配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.auth")
public class AuthProperties implements InitializingBean{

    /* 是否启用认证 */
    private boolean enable = true;

    /* 是否权限同步，将权限刷入数据库 */
    private boolean permissionSync = true;

    /* 是否新增权限 */
    private boolean permissionCreate = true;

    /* 是否更新权限，消耗较大，且一般只更新描述，默认为false */
    private boolean permissionUpdate = false;

    /* 是否删除权限，默认保留 */
    private boolean permissionDelete = false;

    /* 是否进行管理员初始化 */
    private boolean userInit = true;

    /* 是否进行角色初始化 */
    private boolean roleInit = true;

    /* 超级管理员用户属性 */
    private UserProperties admin = new UserProperties()
            .setUsername("admin")
            .setPassword("123456")
            .setEmail("1253665625@qq.com")
            .setPhone("18402933805")
            .setEnable(true)
            .setNickName("曲墨")
            .setRealName("陈振远");

    /* 超级管理员角色 */
    private RoleProperties adminRole = new RoleProperties()
            .setName("超级管理员")
            .setDescription("拥有所有权限")
            .setEnable(true);

    /* 默认角色 */
    private RoleProperties defaultRole = new RoleProperties()
            .setName("默认角色")
            .setDescription("用户默认拥有的角色")
            .setEnable(true);

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
