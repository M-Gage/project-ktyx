package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 经验值配置
 * @author LiWenHao
 * @date 2018/7/14 17:29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperienceConfig {
  private Integer experienceConfigId;
  private int insertCustomer;
  private int insertOrder;
  private int insertFollow;
  private int followLike;
  private int followDislike;
  private String companyId;
}
