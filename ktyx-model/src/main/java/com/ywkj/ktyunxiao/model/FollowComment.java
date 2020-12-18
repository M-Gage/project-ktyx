package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author LiWenHao
 * @date 2018/07/09 15:19
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowComment {

  private Integer followCommentId;
  private String followId;
  private String staffId;
  private String staffName;
  private String content;
  private Integer replyCommentId;
  private String companyId;
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

}
