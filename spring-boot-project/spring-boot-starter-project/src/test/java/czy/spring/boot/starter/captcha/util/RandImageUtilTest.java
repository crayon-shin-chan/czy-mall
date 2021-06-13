package czy.spring.boot.starter.captcha.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RandImageUtilTest {

    @Test
    public void base64() {

        log.info(RandImageUtil.base64("Ag4y",100,50,100,2,"JPEG","data:image/jpg;base64,"));

    }
}