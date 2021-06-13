package czy.spring.boot.starter.captcha.ienum;


import czy.spring.boot.starter.common.exception.IError;

public enum CaptcheError implements IError {

    CODE_EXPIRE(601,"验证码过期"),
    CODE_NOT_EXIST(602,"验证码不存在");

    private int code;
    private String message;

    CaptcheError(int code,String message){
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
