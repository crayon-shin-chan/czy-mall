package czy.spring.boot.starter.alipay.pojo.zoloz;

import lombok.Builder;
import lombok.Data;

/**
 * 支付宝实人认证热脸入库参数
 */
@Data
@Builder
public class AlipayZolozAuthenticationCustomerFacemanageCreateParams {

    /** 入库类型，
     *  IDCARD:身份证
     *  ALIPAY_USER:支付宝用户id,
     *  ALIPAY_TEL:手机号入库
     *  CUSTOMER:自定义
     */
    private String facetype;

    /* 入库用户信息，类型为Customer格式JSON */
    private String faceval;

    /* 有效期天数，可选 */
    private String validtimes;

    /* 商户机唯一编码，关键参数 */
    private String devicenum;

    /* 门店编码，可选 */
    private String storecode;

    /* 商户品牌，可选 */
    private String brandcode;

    /* 分组，可选 */
    private String group;

    /* 地域编码，可选 */
    private String areacode;

    /* 业务量规模，可选 */
    private String bizscale;

    /* 人脸产品能力，可选 */
    private String biz_type;

    /* 扩展参数，可选 */
    private String extinfo;

}
