package com.ywkj.ktyunxiao.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品数量金额视图对象
 *
 * @author MiaoGuoZhu
 * @date 2018/3/5 0005 15:46
 */
@Data
public class GoodsAmountVO {

    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品总金额
     */
    private BigDecimal goodsAmount;
    /**
     *数量
     */
    private Integer quantity;
    /**
     *标准属性
     */
    private String standardAttribute;

}
