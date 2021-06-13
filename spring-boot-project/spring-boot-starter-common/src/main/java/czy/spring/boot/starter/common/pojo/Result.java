package czy.spring.boot.starter.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 响应结果类，数据为泛型T */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /* 响应码 */
    private Integer code;
    /* 响应消息 */
    private String message;
    /* 响应数据 */
    private T data;

    /* 成功返回数据 */
    public static Result success(){
        return new Result(200,"请求成功",null);
    }

    /* 成功返回数据 */
    public static <T> Result<T> success(T data){
        return new Result<T>(200,"请求成功",data);
    }



}