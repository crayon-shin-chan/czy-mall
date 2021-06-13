package czy.spring.boot.starter.alipay.service.zoloz;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.ZolozIdentificationUserWebInitializeRequest;
import com.alipay.api.request.ZolozIdentificationUserWebQueryRequest;
import com.alipay.api.response.ZolozIdentificationUserWebInitializeResponse;
import com.alipay.api.response.ZolozIdentificationUserWebQueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import czy.spring.boot.starter.alipay.ienum.ZolozError;
import czy.spring.boot.starter.alipay.pojo.zoloz.AlipayZolozIdentificationUserWebInitializeParams;
import czy.spring.boot.starter.alipay.pojo.zoloz.AlipayZolozIdentificationUserWebQueryParams;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付宝身份服务
 */
@Slf4j
@Service
public class AlipayZolozIdentificationService {

    @Autowired
    private AlipayClient client;

    @Autowired
    private ObjectMapper mapper;

    /**
     * zoloz.identification.user.web.initialize(H5刷脸认证初始化)
     * @param params：刷脸认证初始化参数
     * @return 刷脸认证唯一标识符
     */
    public ZolozIdentificationUserWebInitializeResponse userWebInitialize(AlipayZolozIdentificationUserWebInitializeParams params){
        try{
            ZolozIdentificationUserWebInitializeRequest request = new ZolozIdentificationUserWebInitializeRequest();
            request.setBizContent(this.mapper.writeValueAsString(params));
            ZolozIdentificationUserWebInitializeResponse response = this.client.execute(request);
            if(response.isSuccess()){
                return response;
            } else {
                log.error("支付宝刷脸认证初始化调用失败",response.getSubCode()+","+response.getSubMsg());
                throw new Exception();
            }
        }catch (Exception ex){
            log.error("支付宝刷脸认证初始化失败",ex);
            throw new HttpException(ZolozError.ALIPAY_ZOLOZ_USER_WEB_INITIALIZE_FAIL);
        }
    }

    /**
     * zoloz.identification.user.web.query(H5刷脸认证查询)
     * @param params：查询参数，刷脸认证的唯一标识
     * @return 正常返回无错误，即代表是本人
     */
    public ZolozIdentificationUserWebQueryResponse userWebQuery(AlipayZolozIdentificationUserWebQueryParams params){
        try{
            ZolozIdentificationUserWebQueryRequest request = new ZolozIdentificationUserWebQueryRequest();
            request.setBizContent(this.mapper.writeValueAsString(params));
            ZolozIdentificationUserWebQueryResponse response = this.client.execute(request);
            if(response.isSuccess()){
                return response;
            } else {
                log.error("支付宝刷脸认证查询调用失败",response.getSubCode()+","+response.getSubMsg());
                throw new Exception();
            }
        }catch (Exception ex){
            log.error("支付宝刷脸认证查询失败",ex);
            throw new HttpException(ZolozError.ALIPAY_ZOLOZ_USER_WEB_INITIALIZE_FAIL);
        }
    }

}
