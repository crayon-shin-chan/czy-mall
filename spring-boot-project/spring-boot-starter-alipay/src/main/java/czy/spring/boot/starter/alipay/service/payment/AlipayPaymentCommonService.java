package czy.spring.boot.starter.alipay.service.payment;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.Client;
import com.alipay.easysdk.payment.common.models.*;
import czy.spring.boot.starter.alipay.ienum.PaymentError;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 支付宝支付通用服务
 */
@Slf4j
@Service
public class AlipayPaymentCommonService {

    /**
     * 统一收单交易创建，商户通过该接口进行交易的创建下单
     * @param subject：订单标题
     * @param outTradeNo：商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
     * @param totalAmount：订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     * 如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】
     * @param buyerId：买家的支付宝唯一用户号
     * @return
     */
    public AlipayTradeCreateResponse create(String subject, String outTradeNo, String totalAmount, String buyerId,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayTradeCreateResponse response =  client.create(subject, outTradeNo, totalAmount,buyerId);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单创建失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_CREATE_FAIL);
        }
    }

    /**
     * 统一收单交易查询，该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
     * @param outTradeNo：商户订单号
     */
    public AlipayTradeQueryResponse query(String outTradeNo,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayTradeQueryResponse response =  client.query(outTradeNo);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单查询失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_QUERY_FAIL);
        }
    }

    /**
     * 统一收单交易退款接口
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款
     * 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
     * 一笔退款失败后重新提交，要采用原来的退款单号。
     * 总退款金额不能超过用户实际支付金额
     * @param outTradeNo：订单支付时传入的商户订单号,不能和 trade_no同时为空
     * @param refundAmount：需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     */
    public AlipayTradeRefundResponse refund(String outTradeNo, String refundAmount,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayTradeRefundResponse response =  client.refund(outTradeNo,refundAmount);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单退款失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_REFUND_FAIL);
        }
    }

    /**
     * 统一收单交易关闭接口，用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     * @param outTradeNo：订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
     */
    public AlipayTradeCloseResponse close(String outTradeNo,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayTradeCloseResponse response =  client.close(outTradeNo);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单关闭失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_CLOSE_FAIL);
        }
    }

    /**
     * 统统一收单交易撤销接口
     * 支付交易返回失败或支付系统超时，调用该接口撤销交易。
     * 如果此订单用户支付失败，支付宝系统会将此订单关闭；
     * 如果用户支付成功，支付宝系统会将此订单资金退还给用户。
     * 注意：只有发生支付系统超时或者支付结果未知时可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。
     * 提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     * @param outTradeNo：订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
     */
    public AlipayTradeCancelResponse cancel(String outTradeNo,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayTradeCancelResponse response =  client.cancel(outTradeNo);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单取消失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_CANCEL_FAIL);
        }
    }

    /**
     * 统一收单交易退款查询
     * 商户可使用该接口查询自已通过alipay.trade.refund或alipay.trade.refund.apply提交的退款请求是否执行成功。
     * 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。
     * 如果该接口返回了查询数据，且refund_status为空或为REFUND_SUCCESS，则代表退款成功，
     * 如果没有查询到则代表未退款成功，可以调用退款接口进行重试。
     * 重试时请务必保证退款请求号一致。
     * @param outTradeNo：订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
     * @param outRequestNo：请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
     * @return
     */
    public AlipayTradeFastpayRefundQueryResponse queryRefund(String outTradeNo, String outRequestNo,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayTradeFastpayRefundQueryResponse response =  client.queryRefund(outTradeNo,outRequestNo);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单退款查询失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_REFUND_QUERY_FAIL);
        }
    }

    /**
     * 查询对账单下载地址接口
     * @param billType：账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型：trade、signcustomer；
     *                trade指商户基于支付宝交易收单的业务账单；
     *                signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单。
     * @param billDate：账单时间：日账单格式为yyyy-MM-dd，最早可下载2016年1月1日开始的日账单
     */
    public AlipayDataDataserviceBillDownloadurlQueryResponse downloadBill(String billType, String billDate,@Nullable Client client){
        try{
            if(Objects.isNull(client)){
                client = Factory.Payment.Common();
            }
            AlipayDataDataserviceBillDownloadurlQueryResponse response =  client.downloadBill(billType,billDate);
            return response;
        }catch (Exception ex){
            log.error("支付宝订单下载对账单失败",ex);
            throw new HttpException(PaymentError.ALIPAY_ORDER_BILL_DOWNLOAD_FAIL);
        }
    }

}
