package czy.spring.boot.starter.alipay.config;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import czy.spring.boot.starter.alipay.AlipayProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class AlipayEasyConfig{

    private AlipayProperties alipayProperties;

    public AlipayEasyConfig(AlipayProperties properties){

        this.alipayProperties = properties;
        easyConfig();

    }

    /* easy sdk 配置 */
    private void easyConfig(){

        Config config = new Config();

        URI uri = URI.create(this.alipayProperties.getServerUrl());

        config.protocol = uri.getScheme();
        config.gatewayHost = uri.getHost();

        config.appId = this.alipayProperties.getAppId();

        config.signType = this.alipayProperties.getSignType();


        config.merchantPrivateKey = this.alipayProperties.getPrivateKey();
        config.alipayPublicKey = this.alipayProperties.getAlipayPublicKey();

        config.merchantCertPath = this.alipayProperties.getPrivateKeyPath();
        config.alipayCertPath = this.alipayProperties.getAlipayPublicKeyPath();
        config.alipayRootCertPath = this.alipayProperties.getRootCertPath();

        config.notifyUrl = this.alipayProperties.getNotifyUrl();
        config.encryptKey = this.alipayProperties.getEncryptKey();

        /* 全局设置参数 */
        Factory.setOptions(config);
    }

}
