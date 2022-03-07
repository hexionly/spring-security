package com.hsx.security.security;

import com.hsx.utils.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码处理
 *
 * @author HEXIONLY
 * @date 2022/3/6 17:44
 */
@Component
public class DefaultPasswordEncode implements PasswordEncoder {

    public DefaultPasswordEncode() {
        this(-1);
    }

    public DefaultPasswordEncode(int strLength) {

    }

    /**
     * 加密
     * 使用自己定义的密码加密工具加密
     *
     * @param charSequence ch
     * @return String
     */
    @Override
    public String encode(CharSequence charSequence) {
        return MD5.encrypt(charSequence.toString());
    }

    /**
     * 比较
     *
     * @param charSequence   输入密码
     * @param encodePassword 存储的密码
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence charSequence, String encodePassword) {
        return encodePassword.equals(MD5.encrypt(charSequence.toString()));
    }
}
