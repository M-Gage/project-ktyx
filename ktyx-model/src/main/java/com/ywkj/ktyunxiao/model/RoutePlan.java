package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.util.Date;
/**路线规划模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class RoutePlan {

  /**
   * 员工id
   */
  private String staffId;
  /**
   * 公司id
   */
  private String companyId;
  /**
   *规划id
   */
  private Integer routePlanId;
  /**
   *客户id
   */
  private String customerId;
  /**
   *状态
   */
  private int status;
  /**
   *客户名称
   */
  private String customerName;
  /**
   *纬度
   */
  private Double latitude;
  /**
   *经度
   */
  private Double longitude;
  /**
   *规划当天日期
   */
  private Date routePlanDate;
  /**
   *先后顺序
   */
  private int routePlanSortOrder;
  /**
   *员工名称
   */
  private String staffName;


}
