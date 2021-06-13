package czy.spring.boot.starter.alipay.service.payment;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.app.Client;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import czy.spring.boot.starter.alipay.ienum.PaymentError;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 支付宝APP支付服务
 */
@Slf4j
@Service
public class AlipayPaymentAppService {

    /**
     * 外部商户APP唤起快捷SDK创建订单并支付
     * @param subject：商品的标题/交易标题/订单标题/订单关键字等。
     * @param outTradeNo：商户网站唯一订单号
     * @param totalAmount：订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * return 返回body订单信息，由客户端吊起支付
     */
    public AlipayTradeAppPayResponse pay(String subject, String outTradeNo, String totalAmount, @Nullable Client client){
        try {

            if(Objects.isNull(client)){
                client = Factory.Payment.App();
            }

            AlipayTradeAppPayResponse response = client.pay(subject,outTradeNo,totalAmount);
            return response;
        }catch (Exception ex){
            log.error("支付宝APP支付失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_APP_PAY_FAIL);
        }


    }

}
