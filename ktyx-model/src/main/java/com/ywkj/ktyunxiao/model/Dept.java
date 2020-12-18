package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import lombok.Data;
/**
 * 部门模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dept {

  /**
   * 部门id
   */
  private String deptId;
  /**
   * 部门名称
   */
  private String deptName;
  /**
   *公司id
   */
  private String companyId;

  public void setDeptName(String deptName) {
    this.deptName = StringUtil.replaceSpace(deptName);
  }
}
