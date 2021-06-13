package czy.spring.boot.starter.auth.config;

import czy.spring.boot.starter.auth.config.security.DefaultAccessDeniedHandler;
import czy.spring.boot.starter.auth.config.security.DefaultAuthenticationEntryPoint;
import czy.spring.boot.starter.auth.config.security.DefaultCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** 安全配置，主要是一些对外的web安全配置、会话、用户配置 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /* session注册器 */
    @Lazy
    @Autowired
    private SessionRegistry sessionRegistry;

    /* 访问拒绝处理器 */
    @Autowired
    private DefaultAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private DefaultCorsConfigurationSource corsConfigurationSource;

    /* 需要登陆访问时，默认的认证入口 */
    @Autowired
    private DefaultAuthenticationEntryPoint authenticationEntryPoint;

    /* 密码编码器 */
    @Bean
    @Primary
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(4);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /* 不经过安全框架的路由，忽略swagger路由 */
        web.ignoring().antMatchers(
                "/h2-console",
                "/v2/api-docs",
                "/webjars/**",
                "/swagger-ui.html",
                "/swagger-resources",
                "/swagger-resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* 跨域 */
        http.cors().configurationSource(corsConfigurationSource);
        /* 匿名访问 */
        http.anonymous().principal("anonymous");
        http.securityContext();
        http.servletApi().rolePrefix("");
        /* 会话管理，最大会话数量 */
        http.sessionManagement()
                .maximumSessions(5)
                .sessionRegistry(sessionRegistry);
        /* 异常处理，访问拒绝，需要登陆 */
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        http.csrf().disable();
        /* 禁用多余的headers */
        http.headers().disable();
        http.logout().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
    }

}
