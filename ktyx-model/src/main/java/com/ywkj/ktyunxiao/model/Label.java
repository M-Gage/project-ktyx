package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 标签模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Label {

  /**
   * 标签id
   */
  private Integer labelId;
  /**
   *标签名称
   */
  @NotBlank(message = "标签名称不能空")
  private String labelName;
  /**
   *公司id
   */
  private String companyId;
  /**
   *员工id
   */
  private String staffId;
  /**
   * 员工名称
   */
  private String staffName;
  /**
   * 创建时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /**
   * 是否私有
   */
  private int isPrivate;

  public void setLabelName(String labelName) {
    this.labelName = StringUtil.replaceSpace(labelName);
  }
}
