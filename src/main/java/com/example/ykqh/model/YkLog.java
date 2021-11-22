package com.example.ykqh.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * qh_log
 * @author yk
 */
@Data
public class YkLog implements Serializable {
    /**
     * 自增id
     */
    private Long logId;

    /**
     * 日志对象
     */
    private String logTarget;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 日志调用的接口
     */
    private String logMethod;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 错误日志
     */
    private String logError;

    /**
     * ip
     */
    private String sysIp;

    /**
     * 地址
     */
    private String sysAddr;

    /**
     * 记录时间
     */
    private LocalDateTime modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUser;

    private static final long serialVersionUID = 1L;
}