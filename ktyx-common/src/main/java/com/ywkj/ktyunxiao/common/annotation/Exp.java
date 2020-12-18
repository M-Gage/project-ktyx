package com.ywkj.ktyunxiao.common.annotation;

import com.ywkj.ktyunxiao.common.enums.ExpType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 经验值注解
 * @author LiWenHao
 * @date 2018/07/03 13:40
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exp {
    ExpType value();
}
