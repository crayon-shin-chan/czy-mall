package czy.spring.boot.starter.alipay.pojo.zoloz;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 支付宝刷脸认证初始化参数
 */
@Data
@Builder
public class AlipayZolozIdentificationUserWebInitializeParams {

    /* 商户请求的唯一标识，该标识作为对账的关键信息，商户要保证其唯一性 */
    private String biz_id;

    /* 身份参数 */
    private IdentityParam identity_param;

    /* 环境参数，可选 */
    private MetaInfo metainfo;

    /* 业务扩展参数 */
    private Map<String,Object> extern_param;

    @Data
    @Builder
    public static class IdentityParam{

        /* 身份类型	CERT */
        private String identity_type;
        /* 证件类型	IDCARD */
        private String cert_type;
        /* 姓名	张三 */
        private String cert_name;
        /* 证件号	341135321489324 */
        private String cert_no;
        /* 用户ID，可选 */
        private String user_id;

    }

    @Data
    @Builder
    public static class MetaInfo{

        private String apdidToken;

    }

}
