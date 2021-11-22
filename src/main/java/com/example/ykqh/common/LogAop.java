package com.example.ykqh.common;

import java.lang.annotation.*;

/**
 * @author yk
 */
@Target({ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface LogAop {
    /**
     * 日志目标类
     */
    String logTarget() default "system";

    String logType();
}
