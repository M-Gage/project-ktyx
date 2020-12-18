package com.ywkj.ktyunxiao.common.exception;

/**
 * 参数异常类
 * @author LiWenHao
 * @date 2018/5/4 17:06
 */
public class ParamException extends RuntimeException {

    public ParamException() {
        super("不正确的参数");
    }
}
