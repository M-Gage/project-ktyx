package com.ywkj.ktyunxiao.model;

import lombok.Data;

/**购物车
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class Cart {

  /**
   * 属性id
   */
  private String staffId;
  /**
   *客户id
   */
  private String customerId;
  /**
   *商品id
   */
  private String goodsId;
  /**
   *数量
   */
  private Integer quantity;

}
