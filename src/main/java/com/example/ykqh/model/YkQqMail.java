package com.example.ykqh.model;

import java.io.Serializable;
import lombok.Data;

/**
 * YK_QQ_MAIL
 * @author 
 */
@Data
public class YkQqMail implements Serializable {
    private Integer id;

    private String qqNum;

    private static final long serialVersionUID = 1L;
}