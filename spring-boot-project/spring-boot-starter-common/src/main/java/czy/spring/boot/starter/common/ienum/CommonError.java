package czy.spring.boot.starter.common.ienum;

import czy.spring.boot.starter.common.exception.IError;
import org.springframework.core.annotation.Order;

/**
 * 通用错误类型
 */
@Order(1)
public enum CommonError implements IError {

    /* 未知错误 */
    UNKNOW_ERROR(500,"未知错误"),
    NEED_ID(501,"操作需要ID"),
    ID_NOT_EXISTS(502,"指定ID不存在"),
    NOT_SUPPORT(503,"不支持的操作"),
    USER_NAME_FORMAT_ERROR(504,"用户名格式不正确"),
    PASSWORD_FORMAT_ERROR(505,"密码格式不正确"),
    EMAIL_FORMAT_ERROR(506,"邮箱格式不正确"),
    PHONE_FORMAT_ERROR(507,"手机号格式不正确");

    private int code;
    private String message;

    CommonError(int code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
