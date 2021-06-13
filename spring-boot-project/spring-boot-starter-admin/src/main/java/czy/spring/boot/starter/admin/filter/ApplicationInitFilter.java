package czy.spring.boot.starter.admin.filter;

import czy.spring.boot.starter.admin.constant.ApplicationConstant;
import czy.spring.boot.starter.admin.constant.SessionConstant;
import czy.spring.boot.starter.admin.entity.Application;
import czy.spring.boot.starter.admin.service.ApplicationService;
import czy.spring.boot.starter.common.util.ResponseUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 应用程序appId检测过滤器，这个过滤器需要加入security过滤器链中
 * 不能注册为外部过滤器
 */
@Data
@Component
public class ApplicationInitFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationService applicationService;

    private static final List<AntPathRequestMatcher> IGNORE_PATH = Arrays.asList(
            new AntPathRequestMatcher("/h2-console"),
            new AntPathRequestMatcher("/v2/api-docs"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/swagger-resources"),
            new AntPathRequestMatcher("/swagger-resources/**")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getMethod().equalsIgnoreCase("options")){
            filterChain.doFilter(request,response);
            return;
        }

        for(AntPathRequestMatcher matcher: IGNORE_PATH){
            if(matcher.matches(request)){
                filterChain.doFilter(request,response);
                return;
            }
        }

        String appId = request.getHeader(ApplicationConstant.APP_ID);
        String appSecret = request.getHeader(ApplicationConstant.APP_SECRET);

        if(Objects.isNull(appId)){
            ResponseUtil.render(response,500,"缺少APP_ID",null);
            return;
        }
        Application application = this.applicationService.getByAppId(appId);

        if(Objects.isNull(application)){

            response.sendError(500,"缺少APP_ID");

        }else if(!application.getAppSecret().equals(appSecret)){

            response.sendError(500,"APP_SECRET错误");

        }else{
            /* 应用对象加入会话 */
            request.getSession().setAttribute(SessionConstant.APPLICATION,application);
            filterChain.doFilter(request,response);
        }

    }

}
