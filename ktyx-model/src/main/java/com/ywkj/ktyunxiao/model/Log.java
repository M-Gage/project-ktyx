package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.util.Date;

/**
 * 日志模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
public class Log {

  /**
   * 员工id
   */
  private String staffId;
  /**
   *请求路径
   */
  private String url;
  /**
   *其你去参数
   */
  private String parameters;
  /**
   *请求方式
   */
  private String method;
  /**
   *来源（APP还是后台）
   */
  private String source;
  /**
   *请求的IP地址
   */
  private String ipAddress;
  /**
   *操作时间
   */
  private Date operateTime;

  public Log() {
  }

  public Log(String staffId, String url, String parameters, String method, String source, String ipAddress, Date operateTime) {
    this.staffId = staffId;
    this.url = url;
    this.parameters = parameters;
    this.method = method;
    this.source = source;
    this.ipAddress = ipAddress;
    this.operateTime = operateTime;
  }
}
