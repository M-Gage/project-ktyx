package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 经验值
 * @author LiWenHao
 * @date 2018/7/14 17:29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Experience {

  private Integer experienceId;
  private String staffId;
  private String behavior;
  private String experience;
  private int before;
  private int after;
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date operatingTime;
  private String companyId;


  public Experience() {
  }

  public Experience(String behavior, String experience) {
    this.behavior = behavior;
    this.experience = experience;
  }
}
