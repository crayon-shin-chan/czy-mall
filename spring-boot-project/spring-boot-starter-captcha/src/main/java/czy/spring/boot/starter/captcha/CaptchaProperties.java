package czy.spring.boot.starter.captcha;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 验证码配置属性
 */
@Data
@ConfigurationProperties(prefix = "czy.spring.captcha")
public class CaptchaProperties implements InitializingBean{

    /* 是否启用验证码模块，默认为true */
    private boolean enable = true;

    /* 验证码过期时间，默认120秒 */
    private Duration expireTime = Duration.ofSeconds(120);

    /* 图片验证码位数，默认为4 */
    private int imageCaptchaNumber = 4;

    /* 图片宽度，默认105 */
    private int imageWidth = 105;

    /* 图片高度，默认35 */
    private int imageHeight = 35;

    /* 干扰线的数量，默认200 */
    private int lineCount = 200;

    /* 干扰线的长度=1.414*lineWidth */
    private int lineWidth = 2;

    /* 图片格式，默认jpeg */
    private String imageFormat = "JPEG";

    /* 图片base64前缀 */
    private String base64Prefix = "data:image/jpg;base64,";

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
