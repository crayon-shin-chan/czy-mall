package czy.spring.boot.starter.performance.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    @Pointcut("execution(* czy.spring.boot.starter.*.service..*.*(..)) || execution(* czy.spring.boot.starter.*.controller..*.*(..)) || execution(* czy.spring.boot.starter.*.action.*.*(..)) || execution(* czy.spring.boot.starter.*.repository..*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object executeTime(ProceedingJoinPoint point)throws Throwable{
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append(point.toString());
        builder.append("，");
        builder.append("duration：");
        builder.append(end-start);
        builder.append("ms");
        log.info(builder.toString());
        return result;
    }

}
