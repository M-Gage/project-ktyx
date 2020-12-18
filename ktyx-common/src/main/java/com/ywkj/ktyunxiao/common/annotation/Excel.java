package com.ywkj.ktyunxiao.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LiWenHao
 * @date 2018/4/26 15:49
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {
    String name();
    int index() default 0;
    int width() default 4000;
}
