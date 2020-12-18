package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**
 * 公司模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {

  /**
   * 公司id
   */
  private String companyId;
  /**
   * 公司名称
   */
  private String companyName;
  /**
   *人员上限
   */
  private Integer siteNumber;
  /**
   *地图关键字
   */
  private String mapKeyword;
  /**
   *服务代码
   */
  private String poiNumber;

  /**
   *公司状态
   */
  private int status;
}
