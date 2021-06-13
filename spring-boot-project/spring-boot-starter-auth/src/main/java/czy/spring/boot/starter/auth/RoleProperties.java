package czy.spring.boot.starter.auth;

import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.auth.entity.Role;
import lombok.Data;

import java.util.Set;

/**
 * 角色配置属性
 */
@Data
public class RoleProperties {

    /* 角色名称 */
    private String name;

    /* 角色描述 */
    private String description;

    /* 是否启用 */
    private boolean enable;

    /**
     * 根据ID、权限创建角色
     */
    public Role create(Long id, Set<Authority> authorities){
        return ((Role)new Role().setId(id))
                .setName(name)
                .setDescription(description)
                .setEnable(enable)
                .setAuthorities(authorities);
    }

    /**
     * 根据ID、权限更新角色
     */
    public void update(Role role, Set<Authority> authorities){
        role.setName(name)
                .setDescription(description)
                .setEnable(enable)
                .setAuthorities(authorities);
    }

}
