package com.example.ykqh.utils;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;

/**
 * @author 杨昆
 * @date 2021/6/28 16:03
 * @describe
 */
public class RsaJsonWebKeyUtil {
    public static RsaJsonWebKey rsaJsonWebKey = null;

    private RsaJsonWebKeyUtil() {
    }

    /**
     * 生成一个RSA密钥对，用于签署和验证JWT，包装在JWK中
     */
    public static RsaJsonWebKey getInstance() {
        if (rsaJsonWebKey == null) {
            try {
                rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
                rsaJsonWebKey.setKeyId("yk-2021");
            } catch (JoseException e) {
                e.printStackTrace();
            }
        }
        return rsaJsonWebKey;
    }
}