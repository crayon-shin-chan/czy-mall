package czy.spring.boot.starter.common.advice;

import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.ienum.CommonError;
import czy.spring.boot.starter.common.pojo.Result;
import czy.spring.boot.starter.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * 通用的控制器通知，顺序为100，最后执行
 */
@Slf4j
@Order(0)
@ControllerAdvice
public class CommonControllerAdvice {

    /** 字段校验异常 */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public Result handle(ValidationException ex){
        log.error("捕获校验异常",ex);
        return ResponseUtil.build(500,ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handle(MissingServletRequestParameterException ex){
        log.error("缺少参数异常",ex.getMessage());
        return ResponseUtil.build(500,ex.getMessage());
    }

    /** 业务异常需要返回异常码 */
    @ResponseBody
    @ExceptionHandler(HttpException.class)
    public Result handle(HttpException ex){
        log.error("捕获业务异常",ex);
        return ResponseUtil.build(ex.getIError());
    }

    /** 未知异常开发过程中要逐一排除 */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handle(Exception ex){

        /* 认证异常交给过滤器处理 */
        if(ex instanceof AuthenticationException){
            throw (AuthenticationException)ex;
        }

        /* 访问拒绝异常交给过滤器处理 */
        if(ex instanceof AccessDeniedException){
            throw (AccessDeniedException)ex;
        }

        log.error("捕获未知错误",ex);
        return ResponseUtil.build(CommonError.UNKNOW_ERROR);
    }

}
