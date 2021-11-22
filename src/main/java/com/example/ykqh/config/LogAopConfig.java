package com.example.ykqh.config;

import com.example.ykqh.common.LogAop;
import com.example.ykqh.model.YkLog;
import com.example.ykqh.service.LogService;
import com.example.ykqh.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author 杨昆
 * @date 2021/6/18 13:22
 * @describe 日志切面配置
 */
@Aspect
@Slf4j
@Configuration
public class LogAopConfig {
    private final LogService logService;

    public LogAopConfig(LogService logService) {
        this.logService = logService;
    }

    /**
     * 指定切点的具体位置(也可指定具体方法)
     */
    @Pointcut("@annotation(com.example.ykqh.common.LogAop)")
    public void addLog() {}

    /**
     * 前置操作
     */
    @Before("addLog()")
    public static void beforeLog(){}

    /**
     * 环绕操作
     */
    @Around(value = "addLog()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = new Object();
        // 获取请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        YkLog ykLog = new YkLog();

        // 获取日志目标和类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAop logAop = method.getAnnotation(LogAop.class);

        ykLog.setLogTarget(logAop.logTarget());
        ykLog.setLogType(Integer.valueOf(logAop.logType()));
        ykLog.setLogMethod(request.getRequestURI());
        ykLog.setLogContent(request.getQueryString());
        ykLog.setSysIp(IpUtil.getIpAddr(request));
        ykLog.setModifyTime(LocalDateTime.now());
        ykLog.setModifyUser(0);

        object = joinPoint.proceed();
        logService.insertLogInfo(ykLog);
        return object;
    }
}