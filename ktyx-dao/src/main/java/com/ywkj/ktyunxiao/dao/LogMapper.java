package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Log;
import org.springframework.stereotype.Component;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/4 15:11
 */
@Component
public interface LogMapper {
    /**记录操作日志
     * @param log
     */
    void insertOperationLog(Log log);
}
