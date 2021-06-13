package czy.spring.boot.starter.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import czy.spring.boot.starter.common.validate.constraint.Email;
import czy.spring.boot.starter.common.validate.constraint.Phone;
import czy.spring.boot.starter.common.validate.group.CreateGroup;
import czy.spring.boot.starter.jpa.entity.Address;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 用户对象，实现了{@link UserDetails}接口，可以用于spring-security
 */
@Data
@Entity
@ApiModel(description = "用户实体")
public class User extends BaseEntity implements UserDetails {

    /* 用户名，标识，长度3-12个字符 */
    @NotNull
    @Length(min = 3,max = 12)
    @ApiModelProperty("用户名")
    @Column(unique = true,nullable = false,length = 40)
    private String username;

    /* 真名，2-10个字符 */
    @Length(min = 2,max = 5)
    @ApiModelProperty("用户真名")
    @Column(length = 10)
    private String realName;

    /* 昵称,2-12个字符 */
    @Length(min = 2,max = 12)
    @ApiModelProperty("用户昵称")
    @Column(unique = true,length = 40)
    private String nickName;

    /* 手机号码 */
    @Phone
    @NotNull
    @ApiModelProperty("手机号")
    @Column(unique = true,length = 20)
    private String phone;

    /* 个人邮箱 */
    @Email
    @NotNull
    @ApiModelProperty("邮箱")
    @Column(unique = true,length = 20)
    private String email;

    /* 加密密码，密码列不可查询，不可更新，创建时非空 */
    @NotNull(groups = CreateGroup.class)
    @ApiModelProperty("用户密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /* 用户头像 */
    @Lob
    @ApiModelProperty("用户头像")
    private String avatar;

    /* 地址 */
    @ApiModelProperty("用户地址")
    private Address address;

    /* 是否启用 */
    @NotNull
    @ApiModelProperty("是否启用")
    @Column(nullable = false)
    private Boolean enable;

    /* 用户拥有角色列表，用户与角色一对多关系 */
    @ManyToMany
    @ApiModelProperty("用户角色列表")
    private Set<Role> roles;

    /** {@link UserDetails}方法 */

    /* 权限字段，非序列化 */
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 返回用户拥有的权限列表
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enable;
    }

    /* 重写equals 和 hashCode方法，只使用userName，用于会话控制 */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash( username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", enable=" + enable +
                '}';
    }
}
