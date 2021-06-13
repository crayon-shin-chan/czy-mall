package czy.spring.boot.starter.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import czy.spring.boot.starter.common.exception.IError;
import czy.spring.boot.starter.common.pojo.Result;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Utf8;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 响应构建工具 */
public class ResponseUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /* 成功返回数据 */
    public static <T> Result<T> build(IError IError){
        return new Result<T>(IError.getCode(), IError.getMessage(),null);
    }

    /* 成功返回数据 */
    public static <T> Result<T> build(T data){
        return new Result<T>(200,null,data);
    }

    /* 成功返回数据，指定消息 */
    public static <T> Result<T> build(String message,T data){
        return new Result<T>(200,message,data);
    }

    /* 构建响应静态方法 */
    public static <T> Result<T> build(Integer code,String message){
        return new Result<T>(code,message,null);
    }

    /* 构建响应静态方法 */
    public static <T> Result<T> build(Integer code,String message,T data){
        return new Result<T>(code,message,data);
    }

    /* 对响应对象渲染错误输出 */
    public static void renderException(HttpServletResponse response, IError IError)throws IOException {
        Result result = build(IError);
        renderResult(response,result);
    }

    /* 对响应对象渲染指定输出 */
    public static void render(HttpServletResponse response, Integer code, String message, Object data)throws IOException {
        Result result = build(code,message,data);
        renderResult(response,result);
    }

    public static void renderResult(HttpServletResponse response, Result result)throws IOException{
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ServletOutputStream out = response.getOutputStream();
        out.write(Utf8.encode(objectMapper.writeValueAsString(result)));
    }

}