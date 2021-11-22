package com.example.ykqh.utils;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 杨昆
 * @date 2021/6/24 10:37
 * @describe AES加解密
 */
public class AesUtil{
    /**
     * 密钥（16位字符串）
     */
    private static final String KEY = "KT-YK-2021-MYSQL";
    /**
     * IV值(16位字符串)
     */
    private static final String IV = "YANG-KING-SPRING";
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加密-解密算法 / 工作模式 / 填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /** 额外知识点
     * 1. 工作模式有CBC(有向量模式)和ECB(无向量模式)，向量模式可以简单理解为偏移量，使用CBC模式需要定义一个IvParameterSpec对象
     * 2. NoPadding: 加密内容不足8位用0补足8位, Cipher类不提供补位功能，需自己实现代码给加密内容添加0, 如{65,65,65,0,0,0,0,0}
     * 3. PKCS5Padding: 加密内容不足8位用余位数补足8位, 如{65,65,65,5,5,5,5,5}或{97,97,97,97,97,97,2,2}; 刚好8位补8位8
     *
     * Cipher的初始化inti()方法介绍：init(int opmode, Key key, AlgorithmParameterSpec params)
     * 1. opmode：Cipher.ENCRYPT_MODE(加密模式)和 Cipher.DECRYPT_MODE(解密模式)
     * 2. key：密匙，使用传入的盐构造出一个密匙，可以使用SecretKeySpec、KeyGenerator和KeyPairGenerator创建密匙，其中
     * SecretKeySpec和KeyGenerator支持AES、DES、DESede三种加密算法创建密匙，KeyPairGenerator支持RSA加密算法创建密匙
     * 3. params ：使用CBC模式时必须传入该参数，该项目使用IvParameterSpec创建iv 对象
     */

    public static String eCode(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8),KEY_ALGORITHM);
        IvParameterSpec spec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.ENCRYPT_MODE,secretKey,spec);
        byte[] content = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(content);
    }

    public static String dCode(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8),KEY_ALGORITHM);
        IvParameterSpec spec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.DECRYPT_MODE,secretKey,spec);
        byte[] content = Base64.getDecoder().decode(text);
        byte[] encrypted = cipher.doFinal(content);
        return new String(encrypted,StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String text = "中国科技大学";
        System.out.println("原文：" + text);
        String a = eCode(text);
        System.out.println("加密后："+ a);
        String b = dCode(a);
        System.out.println("解密后：" + b);
    }
}