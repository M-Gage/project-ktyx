package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**商品模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class Goods {
  /**
   * 商品id
   */
  private String goodsId;
  /**
   *商品编号
   */
  private String goodsNo;
  /**
   *商品名称
   */
  private String goodsName;
  /**
   *组id
   */
  private String groupId;
  /**
   *公司id
   */
  private String companyId;
  /**
   *同步id
   */
  private String syncGoodsId;
  /**
   *零售价
   */
  private BigDecimal retailPrice;
  /**
   *进价
   */
  private BigDecimal purchasePrice;
  /**
   *单位
   */
  private String unit;
  /**
   *类型id
   */
  private String typeId;
  /**
   *库存
   */
  private int stock;
  /**
   *库存预警值
   */
  private int stockWarning;
  /**
   *助记码
   */
  private String helpCode;
  /**
   *备注
   */
  private String remark;
  /**
   *保质时长
   */
  private String expiryDate;
  /**
   *生产日期
   */
  private Date productDate;
  /**
   *最后修改时间
   */
  private Date lastModifyTime;
  /**
   *条形码
   */
  private String barCode;
  /**
   * 类型名称
   */
  private String typeName;
  /**
   *标准属性
   */
  private String standardAttribute;
  /**
   *非标属性
   */
  private String nonstandardAttribute;
  /**
   *辅助属性
   */
  private String assistAttribute;

  public Goods() {
  }

  public Goods(String goodsId, String goodsNo, String goodsName, String groupId, String companyId, String syncGoodsId,
               BigDecimal retailPrice, BigDecimal purchasePrice, String unit, String typeId, int stock, int stockWarning,
               String helpCode, String remark, String expiryDate, Date productDate, Date lastModifyTime, String barCode,
               String typeName, String standardAttribute, String nonstandardAttribute, String assistAttribute) {
    this.goodsId = goodsId;
    this.goodsNo = goodsNo;
    this.goodsName = goodsName;
    this.groupId = groupId;
    this.companyId = companyId;
    this.syncGoodsId = syncGoodsId;
    this.retailPrice = retailPrice;
    this.purchasePrice = purchasePrice;
    this.unit = unit;
    this.typeId = typeId;
    this.stock = stock;
    this.stockWarning = stockWarning;
    this.helpCode = helpCode;
    this.remark = remark;
    this.expiryDate = expiryDate;
    this.productDate = productDate;
    this.lastModifyTime = lastModifyTime;
    this.barCode = barCode;
    this.typeName = typeName;
    this.standardAttribute = standardAttribute;
    this.nonstandardAttribute = nonstandardAttribute;
    this.assistAttribute = assistAttribute;
  }
}
