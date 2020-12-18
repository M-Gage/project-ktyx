package com.ywkj.ktyunxiao.model;

import lombok.Data;
/**角色菜单模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
public class RoleMenu {

  /**
   * 角色id
   */
  private Integer roleId;
  /**
   * 菜单id
   */
  private String menuId;
  /**
   *公司id
   */
  private String companyId;


}
