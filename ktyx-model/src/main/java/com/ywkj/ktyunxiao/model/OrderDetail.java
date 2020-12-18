package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.math.BigDecimal;
/**订单模型
 * @author MiaoGuoZhu
 * @date 2018/5/5 10:17
 */
@Data
public class OrderDetail {

  /**
   * 订单id
   */
  private String orderId;
  /**
   *商品id
   */
  private String goodsId;
  /**
   *商品名称
   */
  private String goodsName;
  /**
   *数量
   */
  private int sum;
  /**
   *商品价格
   */
  private BigDecimal goodsPrice;
  /**
   *类型id
   */
  private String typeId;
  /**
   *类型名称
   */
  private String typeName;

}
