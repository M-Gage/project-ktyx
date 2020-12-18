package com.ywkj.ktyunxiao.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 错误客户模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
public class ErrorCustomer {

  /**
   * id
   */
  private int errorCustomerId;
  /**
   *员工id
   */
  private String staffId;
  /**
   *公司id
   */
  private String companyId;
  /**
   *经度
   */
  private double longitude;
  /**
   *纬度
   */
  private double latitude;
  /**
   *省
   */
  @NotBlank(message = "省份必须填写")
  private String province;
  /**
   *市
   */
  @NotBlank(message = "市必须填写")
  private String city;
  /**
   *区
   */
  @NotBlank(message = "县区必须填写")
  private String district;
  /**
   *创建时间
   */
  private Date createTime;


}
