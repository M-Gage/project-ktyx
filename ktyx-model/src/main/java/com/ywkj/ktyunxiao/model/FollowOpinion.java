package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author LiWenHao
 * @date 2018/07/09 15:19
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowOpinion {

  private Integer followOpinionId;
  private String followId;
  private int opinion;
  private String staffId;
  private String staffName;
  private String companyId;

}
