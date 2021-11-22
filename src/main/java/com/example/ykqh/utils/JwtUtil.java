package com.example.ykqh.utils;

import com.example.ykqh.model.YkUser;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;


/**
 * @author 杨昆
 * @date 2021/6/20 9:59
 * @describe token生成器
 */
public class JwtUtil {
    public static RsaJsonWebKey rsaJsonWebKey = RsaJsonWebKeyUtil.getInstance();

    public static String getJwt(YkUser user) throws JoseException {
        // 创建claims，这将是JWT的内容 B部分
        JwtClaims claims = new JwtClaims();
        // 谁创建了令牌并签署了它
        claims.setIssuer("YangKun");
        // 令牌将被发送给谁
        claims.setAudience("Qh-Consumer");
        // 令牌失效的时间长（从现在开始10分钟）
        claims.setExpirationTimeMinutesInTheFuture(180);
        // 令牌的唯一标识符
        claims.setGeneratedJwtId();
        // 当令牌被发布/创建时（现在）
        claims.setIssuedAtToNow();
        // 在此之前，令牌无效（2分钟前）
        claims.setNotBeforeMinutesInThePast(2);
        // 主题 ,是令牌的对象
        claims.setSubject("");
        // 可以添加关于主题的附加 声明/属性
        claims.setClaim("company", "QiHang");
        claims.setClaim("user",user);

        // JWT是一个JWS和/或一个带有JSON声明的JWE作为有效负载。
        // 在这个例子中，它是一个JWS，所以我们创建一个JsonWebSignature对象。
        JsonWebSignature jws = new JsonWebSignature();

        // JWS的有效负载是JWT声明的JSON内容
        jws.setPayload(claims.toJson());

        // JWT使用私钥签署
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        /*
         * 设置关键ID（kid）头，因为这是一种礼貌的做法。 在这个例子中，我们只有一个键但是使用键ID可以帮助 促进平稳的关键滚动过程
         */
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // 在jw/jws上设置签名算法，该算法将完整性保护声明
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        /*
         * 签署JWS并生成紧凑的序列化或完整的jw/JWS 表示，它是由三个点（'.'）分隔的字符串
         * 在表单头.payload.签名中使用base64url编码的部件 如果你想对它进行加密，你可以简单地将这个jwt设置为有效负载
         * 在JsonWebEncryption对象中，并将cty（内容类型）头设置为“jwt”。
         */
        return jws.getCompactSerialization();
    }

    public static boolean checkJwt(String jwt) throws Exception {
        /*
         * 使用JwtConsumer builder构建适当的JwtConsumer，它将 用于验证和处理JWT。 JWT的具体验证需求是上下文相关的， 然而,
         * 通常建议需要一个（合理的）过期时间，一个受信任的时间 发行人, 以及将你的系统定义为预期接收者的受众。
         * 如果JWT也被加密，您只需要提供一个解密密钥对构建器进行解密密钥解析器。
         */
        JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("YangKun")
                .setExpectedAudience("Qh-Consumer")
                .setVerificationKey(rsaJsonWebKey.getKey())
                .setJwsAlgorithmConstraints(
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                                AlgorithmIdentifiers.RSA_USING_SHA256))
                .build();

        try {
            // 验证JWT并将其处理为jwtClaims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            //如果JWT失败的处理或验证，将会抛出InvalidJwtException。
            return true;
        } catch (InvalidJwtException e) {
            // 对JWT无效的（某些）特定原因的编程访问也是可能的
            // 在某些情况下，您是否需要不同的错误处理行为。
            // JWT是否已经过期是无效的一个常见原因
            if (e.hasExpired()) {
                throw new Exception("JWT已过期，请重新登录");
            }
            // 或者观众是无效的
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                throw new Exception("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
            throw new Exception("JWT无效!，原因为： " + e);
        }
    }

}