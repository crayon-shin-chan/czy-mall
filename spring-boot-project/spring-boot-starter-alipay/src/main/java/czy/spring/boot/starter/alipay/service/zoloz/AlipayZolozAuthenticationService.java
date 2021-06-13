package czy.spring.boot.starter.alipay.service.zoloz;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.ZolozAuthenticationCustomerFacemanageCreateRequest;
import com.alipay.api.request.ZolozAuthenticationCustomerFacemanageDeleteRequest;
import com.alipay.api.response.ZolozAuthenticationCustomerFacemanageCreateResponse;
import com.alipay.api.response.ZolozAuthenticationCustomerFacemanageDeleteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import czy.spring.boot.starter.alipay.ienum.ZolozError;
import czy.spring.boot.starter.alipay.pojo.zoloz.AlipayZolozAuthenticationCustomerFacemanageCreateParams;
import czy.spring.boot.starter.alipay.pojo.zoloz.AlipayZolozAuthenticationCustomerFacemanageDeleteParams;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付宝实人认证服务
 */
@Slf4j
@Service
public class AlipayZolozAuthenticationService {

    @Autowired
    private AlipayClient client;

    @Autowired
    private ObjectMapper mapper;

    /**
     * zoloz.authentication.customer.facemanage.create(热脸入库)
     */
    public ZolozAuthenticationCustomerFacemanageCreateResponse customerFacemanageCreate(AlipayZolozAuthenticationCustomerFacemanageCreateParams params){

        try{
            ZolozAuthenticationCustomerFacemanageCreateRequest request = new ZolozAuthenticationCustomerFacemanageCreateRequest();
            request.setBizContent(this.mapper.writeValueAsString(params));
            ZolozAuthenticationCustomerFacemanageCreateResponse response = this.client.execute(request);
            if(response.isSuccess()){
                return response;
            } else {
                log.error("支付宝热脸入库调用失败",response.getSubCode()+","+response.getSubMsg());
                throw new Exception();
            }
        }catch (Exception ex){
            log.error("支付宝热脸入库失败",ex);
            throw new HttpException(ZolozError.ALIPAY_ZOLOZ_CUSTOMER_FACEMANAGE_CREATE_FAIL);
        }
    }

    /**
     * zoloz.authentication.customer.facemanage.delete(热脸出库)
     */
    public ZolozAuthenticationCustomerFacemanageDeleteResponse customerFacemanageDelete(AlipayZolozAuthenticationCustomerFacemanageDeleteParams params){

        try{
            ZolozAuthenticationCustomerFacemanageDeleteRequest request = new ZolozAuthenticationCustomerFacemanageDeleteRequest();
            request.setBizContent(this.mapper.writeValueAsString(params));
            ZolozAuthenticationCustomerFacemanageDeleteResponse response = this.client.execute(request);
            if(response.isSuccess()){
                return response;
            } else {
                log.error("支付宝热脸出库调用失败",response.getSubCode()+","+response.getSubMsg());
                throw new Exception();
            }
        }catch (Exception ex){
            log.error("支付宝热脸出库失败",ex);
            throw new HttpException(ZolozError.ALIPAY_ZOLOZ_CUSTOMER_FACEMANAGE_DELETE_FAIL);
        }
    }


}
