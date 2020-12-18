package com.ywkj.ktyunxiao.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 各种报表视图对象
 *
 * @author MiaoGuoZhu
 * @date 2018/1/26 10:28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementVO {
    /**
     *总金额
     */
    private Double amount;
    /**
     *其他总金额
     */
    private Double total;
    /**
     *数量
     */
    private Integer sum;
    /**
     *排行
     */
    private Integer rank;



    /**
     *对应日期
     */
    private String existsDate;



    /**
     *客户名称
     */
    private String customerName;
    /**
     *员工名称
     */
    private String staffName;
    /**
     *商品类型名称
     */
    private String typeName;
    /**
     *商品名称
     */
    private String goodsName;
    /**
     *部门名称
     */
    private String deptName;
    /**
     *区域名称
     */
    private String areaName;


    /**
     * 饼图的值
     */
    private Integer value;
    /**
     * 饼图的名称
     */
    private String name;



}
