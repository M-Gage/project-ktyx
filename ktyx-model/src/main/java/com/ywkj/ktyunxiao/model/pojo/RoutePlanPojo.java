package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 路线规划录入对象
 *
 * @author MiaoGuoZhu
 * @date 2018/6/9 11:50
 */
@Data
public class RoutePlanPojo {
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 纬度
     */
    private String longitude;
    /**
     * 计划顺序
     */
    private int routePlanSortOrder;
}
