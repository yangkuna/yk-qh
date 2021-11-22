package com.example.ykqh.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨昆
 * @date 2021/11/19 10:45
 * @describe
 */
@Data
public class DingModel implements Serializable {
    private String msgtype;

    private TextModel text;

    private AtModel at;

    public DingModel(){
        this.msgtype = "text";
    }
}