package czy.spring.boot.starter.alipay.ienum;

import czy.spring.boot.starter.common.exception.IError;

/**
 * 支付宝实人认证错误
 */
public enum  ZolozError implements IError {

    ALIPAY_ZOLOZ_USER_WEB_INITIALIZE_FAIL(7101,"支付宝刷脸认证初始化失败"),
    ALIPAY_ZOLOZ_CUSTOMER_FACEMANAGE_CREATE_FAIL(7102,"支付宝热脸入库失败"),
    ALIPAY_ZOLOZ_CUSTOMER_FACEMANAGE_DELETE_FAIL(7103,"支付宝热脸出库失败");

    private int code;
    private String message;

    ZolozError(int code,String message){
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
