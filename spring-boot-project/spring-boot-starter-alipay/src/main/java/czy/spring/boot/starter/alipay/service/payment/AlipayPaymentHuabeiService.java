package czy.spring.boot.starter.alipay.service.payment;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.huabei.Client;
import com.alipay.easysdk.payment.huabei.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.huabei.models.HuabeiConfig;
import czy.spring.boot.starter.alipay.ienum.PaymentError;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 支付宝花呗服务
 */
@Slf4j
@Service
public class AlipayPaymentHuabeiService {

    /**
     * 花呗下单服务
     * @param subject
     * @param outTradeNo
     * @param totalAmount
     * @param buyerId
     * @param extendParams
     * @param client
     * @return
     */
    public AlipayTradeCreateResponse create(String subject, String outTradeNo, String totalAmount, String buyerId, HuabeiConfig extendParams, @Nullable Client client){

        try {

            if(Objects.isNull(client)){
                client = Factory.Payment.Huabei();
            }
            AlipayTradeCreateResponse response = client.create(subject,outTradeNo,totalAmount,buyerId,extendParams);
            return response;
        }catch (Exception ex){
            log.error("支付宝花呗下单失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_HUABEI_CREATE_FAIL);
        }

    }

}
