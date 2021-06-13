package czy.spring.boot.starter.i18n.ienum;

import czy.spring.boot.starter.common.exception.IError;

public enum I18nError implements IError {

    I18N_CODE_AND_LANG_EXIST(801,"指定语言编码已存在");

    private int code;
    private String message;

    I18nError(int code, String message){
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
