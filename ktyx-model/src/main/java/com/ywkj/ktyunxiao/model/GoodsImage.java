package com.ywkj.ktyunxiao.model;

import lombok.Data;
/**商品图片模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class GoodsImage {


  /**
   * 商品id
   */
  private String goodsId;

  /**
   * 商品图片路径
   */
  private String goodsImagePath;

  public GoodsImage() {
  }

  public GoodsImage(String goodsId, String goodsImagePath) {
    this.goodsId = goodsId;
    this.goodsImagePath = goodsImagePath;
  }
}
