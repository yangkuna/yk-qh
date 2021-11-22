package com.example.ykqh.service;

import com.example.ykqh.dao.SysLogDao;
import com.example.ykqh.model.YkLog;
import org.springframework.stereotype.Service;

/**
 * @author 杨昆
 * @date 2021/6/17 15:51
 * @describe 日志处理
 */
@Service
public class LogService {
    private final SysLogDao sysLogDao;

    public LogService(SysLogDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    public void insertLogInfo(YkLog ykLog){
        sysLogDao.insert(ykLog);
    }
}