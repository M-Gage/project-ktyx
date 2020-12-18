package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**联系人模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {

  /**
   * 联系人id
   */
  private String contactId;
  /**
   * 客户id
   */
  private String customerId;
  /**
   *联系人名称
   */
  private String contactName;
  /**
   *联系人电话
   */
  private String contactPhone;
  /**
   *备注
   */
  private String contactRemark;
  /**
   *是否主联系人
   */
  private int isMain;
  /**
   *公司id
   */
  private String companyId;

  public Contact() {
  }

  public Contact(String contactId, String customerId, String contactName, String contactPhone, int isMain, String companyId) {
    this.contactId = contactId;
    this.customerId = customerId;
    this.contactName = contactName;
    this.contactPhone = contactPhone;
    this.isMain = isMain;
    this.companyId = companyId;
  }
}
