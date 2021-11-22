package com.example.ykqh.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 杨昆
 * @date 2021/6/24 9:33
 * @describe base64加解密
 */
public class Base64Util {
    public static void main(String[] args){
        String a = "中国科技大学";
        String b = Base64.getEncoder().encodeToString(a.getBytes(StandardCharsets.UTF_8));
        System.out.println("b:" + b);

        String c = Base64.getMimeEncoder().encodeToString(a.getBytes(StandardCharsets.UTF_8));
        System.out.println("c:" + c);

        String d = Base64.getUrlEncoder().encodeToString(a.getBytes(StandardCharsets.UTF_8));
        System.out.println("d:" + d);
    }
}