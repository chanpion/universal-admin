package com.chanpion.admin.system.utils;

import com.chanpion.admin.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author April Chen
 * @date 2019/9/6 16:20
 */
public class ShiroUtil {
    private static SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "md5";
    public static final int HASH_ITERATIONS = 2;

    /**
     * 加密用户密码
     *
     * @param user
     * @return
     */
    public static String encryptPassword(User user) {
        String salt = user.getSalt();
        if (salt == null) {
            salt = randomSalt();
            user.setSalt(salt);
        }
        return encryptPassword(user.getPassword(), salt);
    }

    /**
     * 加密密码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String encryptPassword(String password, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = randomSalt();
        }
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    private static String randomSalt() {
        return secureRandom.nextBytes().toHex();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    /**
     * 登出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
}
