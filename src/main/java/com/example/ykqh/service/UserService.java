package com.example.ykqh.service;

import com.alibaba.fastjson.JSONObject;
import com.example.ykqh.dao.RoleDao;
import com.example.ykqh.dao.UserDao;
import com.example.ykqh.model.YkUser;
import com.example.ykqh.netty.MyChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author 杨昆
 * @date 2021/6/17 8:54
 * @describe
 */
@Slf4j
@Service
public class UserService {
    private final JavaMailSender javaMailSender;

    private final UserDao userDao;

    private final RoleDao roleDao;

    public UserService(JavaMailSender javaMailSender, UserDao userDao, RoleDao roleDao) {
        this.javaMailSender = javaMailSender;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Value("${spring.mail.username}")
    private String formMail;

    @Value("${myselfName}")
    private String userName;

    public YkUser getUserInfo(String userCode, String pwd){
        return userDao.selectByPrimaryKey(userCode,pwd);
    }

    public YkUser getUserInfoById(Integer id){
        return userDao.getUserInfoById(id);
    }

    public YkUser insertUserInfo(YkUser ykUser){
        userDao.insert(ykUser);
        return ykUser;
    }

    public YkUser updateUserInfo(YkUser ykUser){
        userDao.updateByPrimaryKey(ykUser);
        return ykUser;
    }

    public List<YkUser> getAllUserInfo(){
        return userDao.getAllUserInfo();
    }

    public void sendAttachmentsMail(List<String> to) throws Exception {
        RestTemplate template = new RestTemplate();
        String data = template.getForObject("http://open.iciba.com/dsapi/",String.class);
        JSONObject object = JSONObject.parseObject(data);
        String note = object.getString("note");
        String subject = "我来看你了！";
        String picture4 = object.getString("picture4");
        MimeMessage message = javaMailSender.createMimeMessage();
        String[] list = to.toArray(new String[0]);
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"utf-8");
            messageHelper.setFrom(formMail,userName);
            messageHelper.setTo(list);
            messageHelper.setSubject(subject);
            messageHelper.setText(note,true);
            //携带附件
            UrlResource urlResource = new UrlResource(picture4);
            messageHelper.addAttachment(LocalDate.now() + ".jpg",urlResource);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public byte[] download(String urlString) throws Exception {
        try {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        InputStream inputStream = con.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
            }
            return outputStream.toByteArray();
        }
        catch (Exception e){
            throw new Exception(e.getCause());
        }
    }
}