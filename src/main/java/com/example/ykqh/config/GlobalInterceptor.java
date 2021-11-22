package com.example.ykqh.config;

import com.example.ykqh.common.CodeEnum;
import com.example.ykqh.common.PassToken;
import com.example.ykqh.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 杨昆
 * @date 2021/6/29 15:23
 * @describe 全局拦截器
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 取出请求方法上的注释，是否带有PassToken以及是否required，required默认为false
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
            else {
                throw new Exception(CodeEnum.TOKEN_SET_ERROR.getMsg());
            }
        }
        else {
            String token = request.getHeader("token");
            if(null == token){
                throw new Exception(CodeEnum.TOKEN_LOSE.getMsg());
            }
            else {
                return JwtUtil.checkJwt(token);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String method = request.getRequestURI();
        String params = request.getQueryString();
        log.info("请求路径：{}；请求参数：{}",method,params);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}