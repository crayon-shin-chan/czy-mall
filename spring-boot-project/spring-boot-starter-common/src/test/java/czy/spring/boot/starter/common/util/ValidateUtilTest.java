package czy.spring.boot.starter.common.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateUtilTest {

    @Test
    public void isUserName() {

        /* 首字母验证 */
        assertTrue(ValidateUtil.isUserName("aaaaaa"));
        assertFalse(ValidateUtil.isUserName("0aaaaa"));
        assertFalse(ValidateUtil.isUserName("_aaaaa"));

        /* 长度验证 */
        assertFalse(ValidateUtil.isUserName("aaaaa"));
        assertFalse(ValidateUtil.isUserName("aaabbbcccddde"));

        /* 字符验证 */
        assertFalse(ValidateUtil.isUserName("aaaa张三"));
        assertFalse(ValidateUtil.isUserName("aaaa.."));
        assertFalse(ValidateUtil.isUserName("aaaa**"));
    }

    @Test
    public void isPassword() {
    }

    @Test
    public void isPhone() {
    }

    @Test
    public void isEmail() {
    }

    @Test
    public void isChinese() {
    }

    @Test
    public void isIDCard() {
    }

    @Test
    public void isUrl() {
    }

    @Test
    public void isIPAddress() {
    }
}