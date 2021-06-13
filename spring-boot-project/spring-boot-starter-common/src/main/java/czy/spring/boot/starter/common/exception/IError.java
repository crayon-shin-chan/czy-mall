package czy.spring.boot.starter.common.exception;

/**
 * 错误接口，可以被枚举类继承
 */
public interface IError {

    int getCode();

    void setCode(int code);

    String getMessage();

    void setMessage(String message);

}
