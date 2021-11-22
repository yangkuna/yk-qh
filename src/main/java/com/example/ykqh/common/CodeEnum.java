package com.example.ykqh.common;

/**
 * @author yk
 */
public enum CodeEnum {
    // 操作类型
    SUCCESS("200","成功"),
    ERROR("201","失败"),

    // 日志类型
    LOG_ADD("1","增"),
    LOG_UPDATE("2","改"),
    LOG_DELETE("3","删"),
    LOG_LOGIN_IN("4","登入"),
    LOG_LOGIN_OUT("5","登出"),
    LOG_ERROR("9","错误"),

    // 系统提示类
    TOKEN_LOSE("101","当前未登录"),
    TOKEN_SET_ERROR("102","PASS_TOKEN设置错误"),

    USER_LOSE("350","当前用户信息不存在"),

    PARAM_ERROR("301","参数设置错误"),
    ;
    /**
     * 编码
     */
    private String code;

    /**
     * 意义
     */
    private String msg;

    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    CodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
