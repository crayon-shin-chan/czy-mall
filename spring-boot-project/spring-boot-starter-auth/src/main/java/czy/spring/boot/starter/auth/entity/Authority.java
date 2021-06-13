package czy.spring.boot.starter.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import czy.spring.boot.starter.auth.constant.AuthorityConstant;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.Set;

/**
 * 权限实体
 * 角色与权限为多对多关系
 */
@Data
@Entity
@ApiModel(description = "角色实体")
public class Authority extends BaseEntity implements GrantedAuthority {

    @ApiModelProperty("权限命名空间")
    @Column(nullable = false,length = 100)
    private String namespace;

    /* 权限名称，类似于编码 */
    @Length(min = 4,max = 30)
    @ApiModelProperty("权限名称")
    @Column(nullable = false,length = 30)
    private String name;

    /* 权限描述 */
    @Column(length = 100)
    @ApiModelProperty("权限描述")
    private String description;

    @ApiModelProperty("权限启用")
    private Boolean enable;

    @ManyToMany(mappedBy = "authorities")
    @ApiModelProperty("权限关联的所有角色")
    private Set<Role> roles;

    @Override
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getAuthority() {
        return namespace+ AuthorityConstant.PERMISSION_JOIN +name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(namespace, authority.namespace) &&
                Objects.equals(name, authority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }

    @Override
    public String toString() {
        return "Authority{" +
                "namespace='" + namespace + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
