package com.ywkj.ktyunxiao.model;

import lombok.Data;
/**属性值模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class AttributeValue {


  /**
   * 属性id
   */
  private Integer attributeId;
  /**
   * 属性值id
   */
  private Integer attributeValueId;
  /**
   * 属性值
   */
  private String attributeValue;
  /**
   * 公司id
   */
  private String companyId;


}
