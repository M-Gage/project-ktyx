package com.ywkj.ktyunxiao.model;

import lombok.Data;
/**日程模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class Schedule {

  /**
   *日程id
   */
  private Integer scheduleId;
  /**
   *员工id
   */
  private String staffId;
  /**
   *公司id
   */
  private String companyId;
  /**
   *客户id
   */
  private String customerId;
  /**
   *内容
   */
  private String content;
  /**
   *提醒时间
   */
  private String remindTime;
  /**
   *开始时间
   */
  private String startTime;
  /**
   *结束时间
   */
  private String endTime;
  /**
   *员工名称
   */
  private String staffName;
  /**
   *客户名称
   */
  private String customerName;

}
