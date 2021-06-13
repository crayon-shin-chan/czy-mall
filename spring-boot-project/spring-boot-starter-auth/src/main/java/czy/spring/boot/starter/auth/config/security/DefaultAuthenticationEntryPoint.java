package czy.spring.boot.starter.auth.config.security;

import czy.spring.boot.starter.auth.ienum.AuthError;
import czy.spring.boot.starter.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 如果当前请求需要认证访问，而当前无认证，则调用此接口 */
@Slf4j
@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(czy.spring.boot.starter.auth.config.security.DefaultAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("认证异常",authException);
        ResponseUtil.renderException(response, AuthError.NEED_LOGIN);
    }
}
