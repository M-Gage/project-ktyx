package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
/**
 * 客户模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

  /**
   * 客户id
   */
  private String customerId;
  /**
   *客户名称
   */
  @NotBlank(message = "客户名称必须填写")
  private String customerName;
  /**
   *客户状态
   */
  private int customerStatus;
  /**
   *客户编号
   */
  @NotBlank(message = "客户编号必须填写")
  private String customerNo;
  /**
   *所属员工id
   */
  private String staffId;
  /**
   * 生日
   */
  private Date birthday;
  /**
   *职员名称
   */
  private String staffName;
  /**
   *经度
   */
  private double longitude;
  /**
   *纬度
   */
  private double latitude;
  /**
   *市
   */
  @NotBlank(message = "市必须填写")
  private String city;
  /**
   *省
   */
  @NotBlank(message = "省份必须填写")
  private String province;
  /**
   *区
   */
  @NotBlank(message = "县区必须填写")
  private String district;
  /**
   *详细地址
   */
  @NotBlank(message = "详细地址必须填写")
  private String detailAddress;
  /**
   *等级
   */
  @NotBlank(message = "等级必须填写")
  private String level;
  /**
   *助记码
   */
  private String helpCode;
  /**
   *创建时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /**
   *最后修改时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastModifyTime;
  /**
   *备注
   */
  private String remark;
  /**
   *同步客户id
   */
  private String syncCustomerId;
  /**
   *公司id
   */
  private String companyId;
  /**
   *订单数量
   */
  private int orderCount;
  /**
   *跟进数量
   */
  private int followCount;
  /**
   *最后下单时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastOrderTime;
  /**
   *最后跟进时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastFollowTime;

  /**
   * 部门id
   */
  private String deptId;

  public Customer() {
  }

  public Customer(String customerId, String customerName, int customerStatus, String customerNo, String staffId, Date birthday, String staffName, double longitude, double latitude,String city, String province,String district,String detailAddress, String level, String helpCode, String remark, String companyId, String deptId) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.customerStatus = customerStatus;
    this.customerNo = customerNo;
    this.staffId = staffId;
    this.birthday = birthday;
    this.staffName = staffName;
    this.longitude = longitude;
    this.latitude = latitude;
    this.city = city;
    this.province = province;
    this.district = district;
    this.detailAddress = detailAddress;
    this.level = level;
    this.helpCode = helpCode;
    this.remark = remark;
    this.companyId = companyId;
    this.deptId = deptId;
  }

  public void setCustomerName(String customerName) {
    this.customerName = StringUtil.replaceSpace(customerName);
  }

  public void setCustomerNo(String customerNo) {
    this.customerNo = StringUtil.replaceSpace(customerNo);
  }
}
