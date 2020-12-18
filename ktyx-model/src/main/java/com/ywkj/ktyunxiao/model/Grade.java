package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 等级
 * @author LiWenHao
 * @date 2018/7/14 17:29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grade {

  private Integer gradeId;
  private String gradeName;
  private Integer gradeExp;
  private String companyId;
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastModifyTime;


}
