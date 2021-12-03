package com.example.ykqh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ykqh.model.AtModel;
import com.example.ykqh.model.DingModel;
import com.example.ykqh.model.TextModel;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author 杨昆
 * @date 2021/11/19 9:53
 * @describe 钉钉对接
 */
@Component
public class DingDingService {

    @Value("${dingding.url}")
    private String dingUrl;

    @Value("${dingding.secret}")
    private String secret;

    public void sendDingDingInfo() throws Exception {
        Long timestamp = System.currentTimeMillis();
        String sign = getSign(timestamp,secret);
        String url = dingUrl + "&timestamp=" + timestamp + "&sign=" + sign;

        DingModel dingModel = new DingModel();
        TextModel textModel = new TextModel();
        textModel.setContent("智能提醒：早上好，你有新的邮件待读取，");
        AtModel atModel = new AtModel();
        dingModel.setAt(atModel);
        dingModel.setText(textModel);
        String jsonString = JSONObject.toJSONString(dingModel);
        jsonString = jsonString.replace("atAll","isAtAll");
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> response = template.postForEntity(
                url, entity, String.class);
        System.out.println(response.getBody());
    }

    private static String getSign(Long timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getCause());
        }
    }

}