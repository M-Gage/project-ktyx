package com.ywkj.ktyunxiao.model;

import lombok.Data;
/**商品类型模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class GoodsType {

  /**
   *类型id
   */
  private String typeId;
  /**
   *类型名称
   */
  private String typeName;
  /**
   *是否父级
   */
  private int isParent;
  /**
   *等级
   */
  private int rank;
  /**
   *前面类型id
   */
  private String preTypeId;
  /**
   *公司id
   */
  private String companyId;

  public GoodsType() {
  }

  public GoodsType(String companyId, String typeId, String preTypeId, String typeName, int isParent, int rank) {
    this.typeId = typeId;
    this.typeName = typeName;
    this.isParent = isParent;
    this.rank = rank;
    this.preTypeId = preTypeId;
    this.companyId = companyId;
  }

}
