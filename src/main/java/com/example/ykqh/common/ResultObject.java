package com.example.ykqh.common;

import lombok.Data;

/**
 * @author 杨昆
 * @date 2021/6/17 14:23
 * @describe 自定义结果集
 */
@Data
public class ResultObject {
    private String code;
    private String msg;
    private Object data;

    public ResultObject(CodeEnum codeEnum){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public ResultObject(CodeEnum codeEnum, Object data){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        this.data = data;
    }
}