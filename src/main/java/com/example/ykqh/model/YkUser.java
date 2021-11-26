package com.example.ykqh.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * user
 * @author yk
 */
@Data
public class YkUser implements Serializable {
    /**
     * 自增id
     */
    private Integer userId;

    /**
     * 手机号码
     */
    private String userPhone;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 性别
     */
    private Integer userSex;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 职位
     */
    private Integer userPosition;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 修改人
     */
    private Integer modifyUser;

    /**
     * 头像
     */
    private byte[] userImg;

    /**
     * 状态（0、1、2）停用、在用、删除
     */
    private Integer state;

    private static final long serialVersionUID = 1L;
}