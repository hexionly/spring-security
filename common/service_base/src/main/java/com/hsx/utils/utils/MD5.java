package com.hsx.utils.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 *
 * @author HEXIONLY
 * @date 2022/3/6 16:07
 */
public class MD5 {

    public static String encrypt(String strSrc) {
        try {
            // 加密后的字符组成
            char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            // 传入的字符转为byte数组
            byte[] bytes = strSrc.getBytes();
            // 使用MD5加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int len = bytes.length;
            char[] chars = new char[len * 2];
            int k = 0;
            for (byte b : bytes) {
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密错误！！！" + e);
        }
    }
}
