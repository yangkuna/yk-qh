package com.example.ykqh.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 杨昆
 * @date 2021/6/23 16:36
 * @describe md5加密
 */
public class Md5Util {
    private static final String SALT = "zxc001";

    /** 常规加密
     * @param inStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMd5(String inStr) throws NoSuchAlgorithmException {
        // MessageDigest类提供信息摘要算法的功能，如 MD5 或 SHA 算法。
        // 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
        // digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            // 每个字节进行与运算 形成16进制的数值，其实 0xff(1111 1111)和任何二进制数值与运算都是其本身
            int val = ((int) md5Byte) & 0xff;
            // 因为16进制是指 0，1，2，3，4，5，6，7，8，9，A,B,C,D,E；前面补充0是为了方便阅读 转换两位一读
            if (val < 16) {
                hexValue.append("0");
            }
            // 转为16位哈希值的字符串形式
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /** 工具类不加盐
     * @param text
     * @return
     */
    public static String getMd5ByUtil(String text){
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /** 工具类加盐
     * @param text
     * @return
     */
    public static String getMd5ByUtilBySalt(String text){
        text = text + "/" + SALT;
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return DigestUtils.md5DigestAsHex(bytes);
    }

}