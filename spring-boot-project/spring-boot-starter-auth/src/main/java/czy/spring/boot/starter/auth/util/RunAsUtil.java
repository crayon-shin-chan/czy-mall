package czy.spring.boot.starter.auth.util;

import czy.spring.boot.starter.auth.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

/**
 * RunAs工具，内部执行线程时，以指定用户身份运行
 */
public class RunAsUtil {

    public static void runAs(Long id,String userName){
        /* 直接设置安全上下文里认证对象即可 */
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        ((User)new User().setId(id)).setUsername(userName),"", Collections.EMPTY_SET)
                );
    }

}
