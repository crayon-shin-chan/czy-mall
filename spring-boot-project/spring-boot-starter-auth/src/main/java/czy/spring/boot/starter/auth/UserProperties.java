package czy.spring.boot.starter.auth;

import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/**
 * 配置用户属性
 */
@Data
public class UserProperties {

    /* 用户名称 */
    private String username;

    /* 用户密码 */
    private String password;

    /* 用户邮箱 */
    private String email;

    /* 用户手机号 */
    private String phone;

    /* 用户昵称 */
    private String nickName;

    /* 用户真名 */
    private String realName;

    /* 是否启用 */
    private boolean enable;

    /* 根据ID、角色创建用户 */
    public User create(Long id, Set<Role> roles, PasswordEncoder encoder){
        return ((User)new User().setId(id))
                .setUsername(this.username)
                .setPassword(encoder.encode(password))
                .setEmail(email)
                .setPhone(phone)
                .setEnable(enable)
                .setNickName(nickName)
                .setRealName(realName)
                .setRoles(roles);
    }

    /* 根据ID、角色更新用户 */
    public void update(User user,Set<Role> roles, PasswordEncoder encoder){
        user.setUsername(this.username)
                .setPassword(encoder.encode(password))
                .setEmail(email)
                .setPhone(phone)
                .setEnable(enable)
                .setNickName(nickName)
                .setRealName(realName)
                .setRoles(roles);
    }

}
