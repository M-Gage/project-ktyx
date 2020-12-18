package com.ywkj.ktyunxiao.model;

import lombok.Data;
/**属性模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class Attribute {

  /**
   * 属性id
   */
  private Integer attributeId;
  /**
   * 属性名称
   */
  private String attributeName;
  /**
   * 是否非标
   */
  private int isNonstandard;
  /**
   * 公司id
   */
  private String companyId;

  public Attribute() {
  }

  public Attribute(String attributeName, int isNonstandard, String companyId) {
    this.attributeName = attributeName;
    this.isNonstandard = isNonstandard;
    this.companyId = companyId;
  }
}
