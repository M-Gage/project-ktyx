package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 16:36
 */
@Component
public interface OrderDetailMapper {
    /**
     * 批量插入订单详细
     * @param orderDetail
     * @return
     */
    int insertList(@Param("orderDetail") List<OrderDetail> orderDetail);

}
