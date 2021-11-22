package com.example.ykqh.common;

import java.lang.annotation.*;

/**
 * @author yk
 */
@Target({ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
