package czy.spring.boot.starter.alipay.pojo.zoloz;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 支付宝刷脸认证查询参数
 */
@Data
@Builder
public class AlipayZolozIdentificationUserWebQueryParams {

    /* 商户请求的唯一标识，该标识作为对账的关键信息，商户要保证其唯一性 */
    private String biz_id;

    /* 刷脸认证的唯一标识，用于查询认证结果 */
    private String zim_id;

    /* 业务扩展参数 */
    private Map<String,Object> extern_param;

}
