package czy.spring.boot.starter.admin.config;

import czy.spring.boot.starter.admin.filter.ApplicationInitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.util.Collections;
import java.util.EnumSet;

/* 管理模块过滤器配置 */
@Order(101)
public class AdminFilterConfig{

    @Autowired
    private ApplicationInitFilter applicationInitFilter;

    @Autowired
    private ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean;

    @Bean
    public FilterRegistrationBean<ApplicationInitFilter> applicationFilterRegistration(){
        FilterRegistrationBean<ApplicationInitFilter> registration = new FilterRegistrationBean();
        registration.setFilter(applicationInitFilter);
        /* 在会话存储过滤器的后面 */
        registration.setOrder(SessionRepositoryFilter.DEFAULT_ORDER+1);
        //registration.addUrlPatterns("/*");
        registration.setServletRegistrationBeans(Collections.singleton(servletServletRegistrationBean));
        registration.setEnabled(true);
        registration.setName(ApplicationInitFilter.class.getName());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
    }
}
