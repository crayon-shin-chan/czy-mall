package czy.spring.boot.starter.alipay.service.payment;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.Client;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import czy.spring.boot.starter.alipay.ienum.PaymentError;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 支付宝电脑网站支付服务
 */
@Slf4j
@Service
public class AlipayPaymentPageService {

    /**
     * 统一收单下单并支付页面接口，用于PC支付
     * @param subject：订单标题
     * @param outTradeNo：商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     * @param totalAmount：订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * @param returnUrl：前端返回页面
     * @return 返回body，网页支付HTML
     */
    public AlipayTradePagePayResponse pay(String subject, String outTradeNo, String totalAmount, String returnUrl, @Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Page();
            }
            AlipayTradePagePayResponse response = client.pay(subject,outTradeNo,totalAmount,returnUrl);
            return response;
        }catch (Exception ex){
            log.error("支付宝PC页面支付失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_PAGE_PAY_FAIL);
        }
    }

}
