package com.example.ykqh.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * role
 * @author yk
 */
@Data
public class YkRole implements Serializable {
    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 修改人
     */
    private Integer modifyUser;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    private static final long serialVersionUID = 1L;
}