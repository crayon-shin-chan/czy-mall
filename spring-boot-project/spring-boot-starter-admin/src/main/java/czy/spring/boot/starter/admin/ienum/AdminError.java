package czy.spring.boot.starter.admin.ienum;

import czy.spring.boot.starter.common.exception.IError;

public enum  AdminError implements IError {

    APPLICATION_NAME_EXIST(601,"应用名称已存在"),
    MENU_PATH_EXIST(602,"菜单路径已存在");

    private int code;
    private String message;

    AdminError(int code,String message){
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
