package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
/**跟进模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Follow {

  /**
   * 跟进id
   */
  private String followId;
  /**
   *客户id
   */
  private String customerId;
  /**
   *客户名称
   */
  private String customerName;
  /**
   *公司id
   */
  private String companyId;
  /**
   *员工id
   */
  private String staffId;
  /**
   *员工名称
   */
  private String staffName;
  /**
   *内容
   */
  private String content;
  /**
   *创建时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /**
   *录音路径
   */
  private String voicePath;

  public Follow() {
  }

  public Follow(String followId, String customerId, String customerName, String companyId, String staffId, String staffName, String content) {
    this.followId = followId;
    this.customerId = customerId;
    this.customerName = customerName;
    this.companyId = companyId;
    this.staffId = staffId;
    this.staffName = staffName;
    this.content = content;
  }
}
