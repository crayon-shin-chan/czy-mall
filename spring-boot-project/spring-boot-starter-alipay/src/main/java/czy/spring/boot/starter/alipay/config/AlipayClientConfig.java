package czy.spring.boot.starter.alipay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import czy.spring.boot.starter.alipay.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayClientConfig{

    @Autowired
    private AlipayProperties alipayProperties;

    @Bean
    public AlipayClient alipayClient()throws AlipayApiException{

        CertAlipayRequest request = new CertAlipayRequest();
        request.setServerUrl(alipayProperties.getServerUrl());
        request.setAppId(alipayProperties.getAppId());

        request.setPrivateKey(alipayProperties.getPrivateKey());
        request.setCertContent(alipayProperties.getPrivateKey());
        request.setCertPath(alipayProperties.getPrivateKeyPath());
        request.setAlipayPublicCertContent(alipayProperties.getAlipayPublicKey());
        request.setAlipayPublicCertPath(alipayProperties.getAlipayPublicKeyPath());
        request.setRootCertContent(alipayProperties.getRootCertContent());
        request.setRootCertPath(alipayProperties.getRootCertPath());

        request.setCharset(alipayProperties.getCharset());
        request.setFormat(alipayProperties.getFormat());
        request.setSignType(alipayProperties.getSignType());
        request.setProxyHost(alipayProperties.getProxyHost());
        request.setProxyPort(alipayProperties.getProxyPort());
        request.setEncryptType(alipayProperties.getEncryptType());
        request.setEncryptor(alipayProperties.getEncryptKey());

        AlipayClient client = new DefaultAlipayClient(request);
        return client;
    }




}
