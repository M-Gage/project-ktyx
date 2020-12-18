package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**客户标签
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerLabel {

  /**
   * 客户标签id
   */
  private Integer customerLabelId;
  /**
   *标签id
   */
  private Integer labelId;
  /**
   * 标签名称
   */
  private String labelName;
  /**
   *客户id
   */
  private String customerId;
  /**
   *公司id
   */
  private String companyId;

}
