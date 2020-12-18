package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**
 * 角色模型
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {

  /**
   * 角色id
   */
  private Integer roleId;
  /**
   * 角色名称
   */
  private String roleName;
  /**
   *公司id
   */
  private String companyId;

}
