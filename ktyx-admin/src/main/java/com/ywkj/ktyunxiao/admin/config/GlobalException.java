package com.ywkj.ktyunxiao.admin.config;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.exception.NoticeException;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获
 * @author LiWenHao
 * @date 2018/5/28 12:00
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult runtimeException(RuntimeException e){
        log.error("异常捕获",e);
        return JsonResult.error(Code.ERROR);
    }

    @ExceptionHandler(ParamException.class)
    public JsonResult paramException(ParamException e){
        log.error("参数异常捕获",e);
        return JsonResult.error(Code.PARAM_ERROR);
    }

    @ExceptionHandler(CheckException.class)
    public JsonResult checkException(CheckException e){
        log.error("检验异常捕获",e);
        return JsonResult.error(Code.CHECK_ERROR, e.getMessage());
    }

    @ExceptionHandler(NoticeException.class)
    public JsonResult noticeException(NoticeException e){
        log.error("通知异常捕获",e);
        return JsonResult.error(Code.INSERT_ERROR, e.getMessage());
    }

}
