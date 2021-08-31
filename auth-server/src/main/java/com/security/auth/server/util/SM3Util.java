package com.security.auth.server.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>国密SM3加密 </p>
 * <p>静态方法</p>
 *
 * @author 王森明
 * @date 2021/4/1 11:21
 * @since 1.0.0
 */
@Slf4j
public class SM3Util {
    private static final String SM3_SALT = "KING-YUAN-JUN";
    /**
     * 位置必须在字符串长度以内
     */
    private static final int SM3_SALT_POSITION = 1;

    public static String encode(String plain) throws RuntimeException {
        try {
            return SM3.digestHex(plain);
        } catch (Exception e) {
            log.info("加密异常：{}", e.getMessage(), e);
            throw new RuntimeException("加密异常：" + e.getMessage());
        }
    }

    public static String encodeWithSalt(String plain) throws RuntimeException {
        try {
            return SM3.digestHex(plain + SM3_SALT);
        } catch (Exception e) {
            log.info("加密异常：{}", e.getMessage(), e);
            throw new RuntimeException("加密异常：" + e.getMessage());
        }
    }

    public static String encodeWithSalt(String plain, String salt) throws RuntimeException {
        try {
            salt = StringUtils.isBlank(salt) ? SM3_SALT : salt;
            return SM3.digestHex(plain + salt);
        } catch (Exception e) {
            log.info("加密异常：{}", e.getMessage(), e);
            throw new RuntimeException("加密异常：" + e.getMessage());
        }
    }

    public static String encodeWithSalt(String plain, String salt, int saltPosition) throws RuntimeException {
        try {
            salt = StringUtils.isBlank(salt) ? SM3_SALT : salt;
            saltPosition = saltPosition > plain.length() ? SM3_SALT_POSITION : saltPosition;
            return SM3.digestHex(plain.substring(0, saltPosition) + salt + plain.substring(saltPosition));
        } catch (Exception e) {
            log.info("加密异常：{}", e.getMessage(), e);
            throw new RuntimeException("加密异常：" + e.getMessage());
        }
    }
}
