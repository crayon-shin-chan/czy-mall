package czy.spring.boot.starter.captcha.service;

import cn.hutool.core.util.RandomUtil;
import czy.spring.boot.starter.captcha.CaptchaProperties;
import czy.spring.boot.starter.captcha.constant.CaptchaConstant;
import czy.spring.boot.starter.captcha.ienum.CaptcheError;
import czy.spring.boot.starter.captcha.util.RandImageUtil;
import czy.spring.boot.starter.common.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class CaptcheService {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private CaptchaProperties properties;

    public String image(String key){
        /* 获取随机验证码，包含大小写与数字 */
        String code = RandomUtil.randomString(CaptchaConstant.BASE_STRING,properties.getImageCaptchaNumber());
        /* 存入redis中 */
        this.template.opsForValue().set(key+code,String.valueOf(System.currentTimeMillis()),properties.getExpireTime());
        return RandImageUtil.base64(code,properties.getImageWidth(),properties.getImageHeight(),properties.getLineCount(),properties.getLineWidth(),properties.getImageFormat(),properties.getBase64Prefix());
    }

    /**
     * 校验验证码
     * @param key
     * @param code
     */
    public void verify(String key,String code){

        /* 禁用直接返回 */
        if(!this.captchaProperties.isEnable()){
            return;
        }

        String value = this.template.opsForValue().get(key+code);

        /* 验证码过期 */
        if(StringUtils.isEmpty(value)){
            throw new HttpException(CaptcheError.CODE_NOT_EXIST);
        }

        long time = Long.parseLong(value);

        /* 验证码不存在 */
        if((System.currentTimeMillis() - time)>properties.getExpireTime().toMillis()){
            throw new HttpException(CaptcheError.CODE_EXPIRE);
        }

    }

}



