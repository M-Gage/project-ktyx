package com.ywkj.ktyunxiao.model.vo;


import com.ywkj.ktyunxiao.model.OrderDetail;
import lombok.Data;

import java.util.List;

/**
 * 客户订单视图对象
 *
 * @author MiaoGuoZhu
 * @date 2018/1/24 0024 14:55
 */
@Data
public class CustomerOrderVO {

    /**
     * 客户id
     */
    private String customerId;
    /**
     * 省
     */
    private String province;
    /**
     *市
     */
    private String city;
    /**
     *区
     */
    private String district;
    /**
     *详细地址
     */
    private String orderAddress;
    /**
     *客户名称
     */
    private String customerName;
    /**
     * 是否指定客户
     * */
    private Boolean isAppoint;

    /**
     *订单详细列表
     */
    private List<OrderDetail> orderDetail;
}
