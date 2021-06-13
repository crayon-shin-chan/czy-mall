package czy.spring.boot.starter.alipay.service.payment;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.facetoface.Client;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import czy.spring.boot.starter.alipay.ienum.PaymentError;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AlipayPaymentFaceToFaceService {

    /**
     * 统一收单交易支付接口，用于条码支付，用户出示付款码，商户扫用户付款码获取付款码支付，接口调用后支付成功
     * 收银员使用扫码设备读取用户手机支付宝“付款码”/声波获取设备（如麦克风）读取用户手机支付宝的声波信息后，将二维码或条码信息/声波信息通过本接口上送至支付宝发起支付。
     * @param subject：订单标题
     * @param outTradeNo：商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     * @param totalAmount：订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * @param authCode：支付授权码，25~30开头的长度为16~24位的数字，
     * @param client
     * @return 返回支付成功的订单信息
     */
    public AlipayTradePayResponse pay(String subject, String outTradeNo, String totalAmount, String authCode, @Nullable Client client){
        try {
            if(Objects.isNull(client)){
                client = Factory.Payment.FaceToFace();
            }
            AlipayTradePayResponse response = client.pay(subject,outTradeNo,totalAmount,authCode);
            return response;
        }catch (Exception ex){
            log.error("支付宝条码支付失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_FACE_TO_FACE_PAY_FAIL);
        }

    }

    /**
     * 统一收单线下交易预创建，收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * 用户支付后通过回调地址来获取支付结果
     * @param subject：订单标题
     * @param outTradeNo：商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     * @param totalAmount：订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * @param client
     * @return 返回二维码链接
     */
    public AlipayTradePrecreateResponse preCreate(String subject, String outTradeNo, String totalAmount, @Nullable Client client){
        try {
            if(Objects.isNull(client)){
                client = Factory.Payment.FaceToFace();
            }

            AlipayTradePrecreateResponse response = client.preCreate(subject,outTradeNo,totalAmount);
            return response;
        }catch (Exception ex){

            log.error("支付宝扫码支付失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_QR_CODE_PAY_FAIL);

        }
    }


}
