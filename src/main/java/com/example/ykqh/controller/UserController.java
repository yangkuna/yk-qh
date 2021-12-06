package com.example.ykqh.controller;

import com.example.ykqh.common.CodeEnum;
import com.example.ykqh.common.PassToken;
import com.example.ykqh.common.ResultObject;
import com.example.ykqh.model.YkUser;
import com.example.ykqh.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 个人测试模块
 * @author 杨昆
 * @date 2021/6/17 9:10
 * @describe 个人测试模块
 */
@RestController
@RequestMapping("/yk-qh/user")
public class UserController {
    /**
     * 公平锁
     */
    private static final Lock LOCK = new ReentrantLock(true);

    private final UserService userService;

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate<String,Object> redisTemplate;

    public UserController(UserService userService, StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    /** 获取人员信息
     * @param id 主键id
     * @return 自定义结果
     * @throws Exception 错误类
     */
    @GetMapping("/getUserInfo")
    @PassToken
    public ResultObject getUserInfo(Integer id) throws Exception {
        YkUser user =(YkUser) redisTemplate.opsForHash().get(String.valueOf(id),"TEST_KEY");
        if(null == user){
            LOCK.lock();
            try {
                YkUser ykUser = userService.getUserInfoById(id);
                redisTemplate.opsForHash().put(String.valueOf(id),"TEST_KEY", ykUser);
                redisTemplate.expire(String.valueOf(id),30,TimeUnit.SECONDS);
                return new ResultObject(CodeEnum.SUCCESS, ykUser);
            }
            catch (Exception e){
                throw new Exception(e.getMessage());
            }
            finally {
                LOCK.unlock();
            }
        }
        return new ResultObject(CodeEnum.SUCCESS, user);
    }

    /** 插入患者数据
     * @param ykUser 实体类
     * @return 自定义结果
     */
    @PostMapping("/insertUserInfo")
    public ResultObject insertUserInfo(@RequestBody YkUser ykUser){
        return new ResultObject(CodeEnum.SUCCESS,userService.insertUserInfo(ykUser));
    }

    /** 邮件发送接口
     * @param toMail 接收人邮箱地址
     */
    @GetMapping("/sendMailEveryDay")
    @PassToken
    public ResultObject sendMailEveryDay(String toMail){
        try {
            List<String> list = new ArrayList<>();
            list.add(toMail);
            userService.sendAttachmentsMail(list);
            return new ResultObject(CodeEnum.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResultObject(CodeEnum.ERROR,e.getMessage());
        }
    }

}