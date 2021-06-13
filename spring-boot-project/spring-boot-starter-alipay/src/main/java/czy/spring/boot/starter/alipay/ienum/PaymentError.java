package czy.spring.boot.starter.alipay.ienum;

import czy.spring.boot.starter.common.exception.IError;

public enum  PaymentError implements IError {

    ALIPAY_ORDER_CREATE_FAIL(7001,"支付宝订单创建失败"),
    ALIPAY_ORDER_QUERY_FAIL(7002,"支付宝订单查询失败"),
    ALIPAY_ORDER_REFUND_FAIL(7003,"支付宝订单退款失败"),
    ALIPAY_ORDER_CLOSE_FAIL(7004,"支付宝订单关闭失败"),
    ALIPAY_ORDER_CANCEL_FAIL(7005,"支付宝订单取消失败"),
    ALIPAY_ORDER_REFUND_QUERY_FAIL(7006,"支付宝订单退款查询失败"),
    ALIPAY_ORDER_BILL_DOWNLOAD_FAIL(7007,"支付宝下载对账单失败"),
    ALIPAY_ORDER_PAGE_PAY_FAIL(7008,"支付宝PC页面支付失败"),
    ALIPAY_ORDER_WAP_PAY_FAIL(7009,"支付宝手机页面支付失败"),
    ALIPAY_ORDER_APP_PAY_FAIL(7010,"支付宝APP支付失败"),
    ALIPAY_ORDER_HUABEI_CREATE_FAIL(7011,"支付宝花呗下单失败"),
    ALIPAY_ORDER_FACE_TO_FACE_PAY_FAIL(7012,"支付宝条码支付失败"),
    ALIPAY_ORDER_QR_CODE_PAY_FAIL(7013,"支付宝扫码支付失败");

    private int code;
    private String message;

    PaymentError(int code,String message){
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
