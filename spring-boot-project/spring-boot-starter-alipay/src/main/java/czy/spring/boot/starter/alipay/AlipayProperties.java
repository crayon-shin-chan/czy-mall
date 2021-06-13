package czy.spring.boot.starter.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付宝模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.alipay")
public class AlipayProperties {

    /* 是否启用支付宝模块 */
    private boolean enable = true;

    /* 服务地址，默认为正式地址，可以替换为测试地址 */
    private String serverUrl = "https://openapi.alipay.com/gateway.do";

    /* 应用ID */
    private String appId;

    /* 应用私钥，用于签名 */
    private String privateKey;

    /* 应用私钥文件路径，用于签名 */
    private String privateKeyPath;

    /* 支付宝公钥，用于验签 */
    private String alipayPublicKey;

    /* 支付宝公钥文件路径，用于验签 */
    private String alipayPublicKeyPath;

    /* 根证书内容 */
    private String rootCertContent;

    /* 根证书路径 */
    private String rootCertPath;

    /* 响应类型 */
    private String format = "json";

    /* 字符集 */
    private String charset = "UTF-8";

    /* 签名类型RSA2 */
    private String signType = "RSA2";

    /* 代理域名 */
    private String proxyHost;

    /* 代理端口 */
    private int proxyPort;

    /* 加密类型，只支持AES */
    private String encryptType = "AES";

    /* 加密KEY */
    private String encryptKey;

    /* 异步通知路径 */
    private String notifyUrl;



}
