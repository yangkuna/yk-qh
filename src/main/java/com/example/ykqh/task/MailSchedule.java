package com.example.ykqh.task;

import com.example.ykqh.dao.YkQqMailDao;
import com.example.ykqh.model.YkQqMail;
import com.example.ykqh.service.DingDingService;
import com.example.ykqh.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨昆
 * @date 2021/12/1 15:12
 * @describe 每日定时发送邮件
 */
@Component
public class MailSchedule {
    private final String CRON_TIME = "0 0 8 * * ?";

    private final YkQqMailDao mailDao;

    private final UserService userService;

    private final DingDingService dingService;

    public MailSchedule(YkQqMailDao mailDao, UserService userService, DingDingService dingService) {
        this.mailDao = mailDao;
        this.userService = userService;
        this.dingService = dingService;
    }

    @Scheduled(cron = CRON_TIME)
    public void setQqMails() throws Exception {
        List<YkQqMail> list = mailDao.selectAllMail();
        if(list.size() > 0){
            List<String> mailList = new ArrayList<>();
            for(YkQqMail mail:list){
                mailList.add(mail.getQqNum() + "@qq.com");
            }
            userService.sendAttachmentsMail(mailList);
            dingService.sendDingDingInfo();
        }
    }
}