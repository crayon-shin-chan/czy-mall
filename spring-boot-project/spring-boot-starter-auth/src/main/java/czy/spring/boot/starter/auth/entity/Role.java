package czy.spring.boot.starter.auth.entity;

import czy.spring.boot.starter.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

/**
 * 角色实体
 * 用户与角色为多对多关系
 * 角色与权限为多对多关系
 */
@Data
@Entity
@ApiModel(description = "角色实体")
public class Role extends BaseEntity {

    /* 角色名称 */
    @NotNull
    @Length(min = 3,max = 15)
    @ApiModelProperty("角色名称")
    @Column(unique = true,nullable = false,length = 30)
    private String name;

    /* 角色描述 */
    @NotNull
    @Column(length = 100)
    @ApiModelProperty("角色描述")
    private String description;

    /* 是否启用 */
    @NotNull
    @ApiModelProperty("是否启用")
    @Column(nullable = false)
    private Boolean enable;

    /* mappedBy要放在从实体上，这边的集合不会影响关系 */
    @ManyToMany(mappedBy = "roles")
    @ApiModelProperty("角色下所有用户")
    private Set<User> users;

    @ManyToMany
    @ApiModelProperty("权限列表")
    private Set<Authority> authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

}
