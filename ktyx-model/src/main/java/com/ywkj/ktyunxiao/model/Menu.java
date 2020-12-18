package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/**菜单模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {

  /**
   * 菜单id
   */
  private String menuId;
  /**
   *菜单名称
   */
  private String menuName;
  /**
   *菜单对应路径
   */
  private String menuUrl;
  /**
   *菜单图标
   */
  private String menuIcon;
  /**
   *是否父级菜单
   */
  private String isParent;
  /**
   *父级菜单id
   */
  private String parentId;

}
