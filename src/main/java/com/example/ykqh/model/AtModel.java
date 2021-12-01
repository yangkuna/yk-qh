package com.example.ykqh.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 杨昆
 * @date 2021/11/19 10:49
 * @describe
 */
@Data
public class AtModel implements Serializable {
    private boolean isAtAll;

    @Override
    public String toString() {
        return "AtModel{" +
                "isAtAll=" + isAtAll +
                ", atMobiles=" + atMobiles +
                ", atUserIds=" + atUserIds +
                '}';
    }

    private List<Integer> atMobiles;

    private List<Integer> atUserIds;

    public AtModel(){
        this.isAtAll = true;
    }
}