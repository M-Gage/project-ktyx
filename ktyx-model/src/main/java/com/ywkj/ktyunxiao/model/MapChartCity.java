package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 市地图模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
public class MapChartCity {

  /**
   * 城市id
   */
  private Integer cityId;
  /**
   *省
   */
  private String province;
  /**
   *市
   */
  private String city;
  /**
   *公司id
   */
  private String companyId;
  /**
   *客户数量
   */
  private int customerCount;
  /**
   *总金额
   */
  private BigDecimal amountCount;

  public MapChartCity() {
  }

  public MapChartCity(String province, String city, String companyId, int customerCount, BigDecimal amountCount) {
    this.province = province;
    this.city = city;
    this.companyId = companyId;
    this.customerCount = customerCount;
    this.amountCount = amountCount;
  }
}
