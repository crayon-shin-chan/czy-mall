package czy.spring.boot.starter.alipay.service.payment;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.wap.Client;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import czy.spring.boot.starter.alipay.ienum.PaymentError;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AlipayPaymentWapService {

    /**
     * 外部商户创建订单并支付
     * @param subject：商品的标题/交易标题/订单标题/订单关键字等。
     * @param outTradeNo：商户网站唯一订单号
     * @param totalAmount：订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * @param quitUrl：用户付款中途退出返回商户网站的地址
     * @param returnUrl：支付成功，前台回调地址
     * @return 返回body网页支付HTML，直接渲染即可
     */
    public AlipayTradeWapPayResponse pay(String subject, String outTradeNo, String totalAmount, String quitUrl, String returnUrl,@Nullable Client client){

        try {
            if(Objects.isNull(client)){
                client = Factory.Payment.Wap();
            }
            AlipayTradeWapPayResponse response = client.pay(subject,outTradeNo,totalAmount,quitUrl,returnUrl);
            return response;
        }catch (Exception ex){
            log.error("支付宝手机网页支付失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_WAP_PAY_FAIL);
        }

    }

}
