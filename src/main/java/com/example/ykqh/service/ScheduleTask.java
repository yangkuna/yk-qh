package com.example.ykqh.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 杨昆
 * @date 2021/11/18 9:41
 * @describe 定时任务
 */
@Component
public class ScheduleTask {
    private final JavaMailSender javaMailSender;

    @Value("${myselfName}")
    private String host;

    public ScheduleTask(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 18 10 * * ?")
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(host);
        //收件人
        message.setTo("1553207275@qq.com");
        //邮件主题
        message.setSubject("第一次测试");
        //邮件内容
        message.setText("早上好，朋友们");
        //发送邮件
        javaMailSender.send(message);
    }

}