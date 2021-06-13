package czy.spring.boot.starter.common.util;

import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.ienum.CommonError;

import java.util.regex.Pattern;

import static czy.spring.boot.starter.common.constant.RegexConstant.*;

/**
 * 验证工具，用于验证各种类型字符串
 */
public class ValidateUtil {

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    public static ValidateUtil checkUserName(String username){
        if(!isUserName(username)){
            throw new HttpException(CommonError.USER_NAME_FORMAT_ERROR);
        }
        return null;
    }
    
    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static ValidateUtil checkPassword(String password){
        if(!isPassword(password)){
            throw new HttpException(CommonError.PASSWORD_FORMAT_ERROR);
        }
        return null;
    }

    /**
     * 校验手机号
     *
     * @param phone
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhone(String phone) {
        return Pattern.matches(REGEX_PHONE, phone);
    }

    public static ValidateUtil checkPhone(String phone){
        if(!isPhone(phone)){
            throw new HttpException(CommonError.PHONE_FORMAT_ERROR);
        }
        return null;
    }

    /**
     * 校验固定电话号码
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    public static ValidateUtil checkEmail(String email){
        if(!isMobile(email)){
            throw new HttpException(CommonError.EMAIL_FORMAT_ERROR);
        }
        return null;
    }


    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIPAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress);
    }

}
