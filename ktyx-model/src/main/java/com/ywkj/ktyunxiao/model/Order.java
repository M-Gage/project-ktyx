package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**订单模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class Order {

  /**
   *订单id
   */
  private String orderId;
  /**
   *员工id
   */
  private String staffId;
  /**
   *客户id
   */
  private String customerId;
  /**
   *公司id
   */
  private String companyId;
  /**
   *提交时间
   */
  private Date submitTime;
  /**
   *总金额
   */
  private BigDecimal amount;
  /**
   *状态
   */
  private int status;
  /**
   *省
   */
  private String province;
  /**
   *市
   */
  private String city;
  /**
   *区
   */
  private String district;
  /**
   *详细地址
   */
  private String detailAddress;
  /**
   *客户名称
   */
  private String customerName;
  /**
   *员工名称
   */
  private String staffName;
  /**
   *同步id
   */
  private String syncOrderId;


}
