package com.example.ykqh.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * role_user
 * @author yk
 */
@Data
public class RoleUser implements Serializable {
    private Integer roleId;

    private Integer userId;

    private Integer modifyUser;

    private LocalDateTime modifyTime;

    private static final long serialVersionUID = 1L;
}