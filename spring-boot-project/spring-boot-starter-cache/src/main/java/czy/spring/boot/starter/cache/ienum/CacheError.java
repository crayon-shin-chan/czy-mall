package czy.spring.boot.starter.cache.ienum;

import czy.spring.boot.starter.common.exception.IError;

public enum CacheError implements IError {

    CACHE_NAME_NOT_EXISTS(901,"指定名称缓存不存在");

    private int code;
    private String message;

    CacheError(int code,String message){
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
