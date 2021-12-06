package com.example.ykqh.controller;

import com.example.ykqh.common.CodeEnum;
import com.example.ykqh.common.PassToken;
import com.example.ykqh.common.ResultObject;
import com.example.ykqh.model.YkUser;
import com.example.ykqh.service.UserService;
import com.example.ykqh.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.lang.JoseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/** 登录模块
 * @author 杨昆
 * @date 2021/6/20 9:49
 * @describe 登入登出
 */
@Slf4j
@RestController
@RequestMapping("/yk-qh/login")
public class LoginController {
    private final UserService userService;

    private final RedisTemplate<String,Object> redisTemplate;
    public LoginController(UserService userService, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    /** 登录
     * @param userCode 人员编码
     * @param pwd 密码
     * @return 自定义结果
     * @throws JoseException 错误类
     */
    @PassToken()
    @GetMapping("/loginIn")
    public ResultObject loginIn(String userCode, String pwd) throws JoseException {
        Map<String,Object> map = new HashMap<>(2);
        YkUser user = userService.getUserInfo(userCode,pwd);
        if(null == user){
            return new ResultObject(CodeEnum.USER_LOSE);
        }
        else {
            String token = JwtUtil.getJwt(user);
            map.put("token",token);
            map.put("user",user);
        }
        redisTemplate.opsForHash().put(userCode,user.getUserId().toString(),map);
        return new ResultObject(CodeEnum.SUCCESS,map);
    }

    public ResultObject loginOut(Integer id, String pwd){

        return new ResultObject(CodeEnum.SUCCESS);
    }
}