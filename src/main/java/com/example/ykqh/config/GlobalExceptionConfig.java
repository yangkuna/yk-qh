package com.example.ykqh.config;

import com.example.ykqh.common.CodeEnum;
import com.example.ykqh.common.ResultObject;
import com.example.ykqh.model.YkLog;
import com.example.ykqh.service.LogService;
import com.example.ykqh.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author 杨昆
 * @date 2021/6/17 14:50
 * @describe 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionConfig {
    private final LogService logService;

    public GlobalExceptionConfig(LogService logService) {
        this.logService = logService;
    }

    /**
     * 异常处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultObject exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        YkLog ykLog = new YkLog();
        ykLog.setLogTarget("system");
        ykLog.setLogType(Integer.valueOf(CodeEnum.LOG_ERROR.getCode()));
        ykLog.setLogMethod(request.getRequestURI());
        ykLog.setLogContent(request.getQueryString());
        ykLog.setLogError(e.getMessage());
        ykLog.setSysIp(IpUtil.getIpAddr(request));
        ykLog.setModifyTime(LocalDateTime.now());
        // 获取当前会话的人员id 若无 则默认为0
        ykLog.setModifyUser(0);
        logService.insertLogInfo(ykLog);
        return new ResultObject(CodeEnum.ERROR,e.getMessage());
    }

}