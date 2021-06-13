package czy.spring.boot.starter.auth.config.security;


import czy.spring.boot.starter.auth.ienum.AuthError;
import czy.spring.boot.starter.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** 默认访问拒绝处理器，在{@link ExceptionTranslationFilter}中被处理，此过滤器在最外层，可以处理内层过滤器抛出的异常
 *  当{@link org.springframework.security.core.Authentication}没有访问接口权限时，会抛出{@link AccessDeniedException}
 *  由过滤器捕获，交给{@link AccessDeniedHandler}进行处理
 *  此异常不能被Advice捕获
 */
@Slf4j
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("访问拒绝",accessDeniedException.getMessage());
        ResponseUtil.renderException(response, AuthError.ACCESS_DENY);
    }
}
