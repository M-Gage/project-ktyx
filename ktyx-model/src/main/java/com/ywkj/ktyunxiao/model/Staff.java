package com.ywkj.ktyunxiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
/**职员模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Staff{

  /**
   * 员工id
   */
  private String staffId;
  /**
   *电话
   */
  private String phone;
  /**
   *密码
   */
  private String password;
  /**
   *员工编号
   */
  private String staffNo;
  /**
   *员工状态
   */
  private int staffStatus;
  /**
   *部门id
   */
  private String deptId;
  /**
   *部门名称
   */
  private String deptName;
  /**
   *公司id
   */
  private String companyId;
  /**
   *员工名称
   */
  private String staffName;
  /**
   *助记码
   */
  private String helpCode;
  /**
   *性别
   */
  private String sex;
  /**
   *生日
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
  private Date birthday;
  /**
   *是否部门管理员
   */
  private int isDeptManager;
  /**
   *角色id
   */
  private Integer roleId;
  /**
   *角色名称
   */
  private String roleName;
  /**
   *创建时间
   */
  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /**
   *同步id
   */
  private String syncStaffId;
  /**
   * 经验值
   */
  private int experience;

  public Staff() {
  }

  public Staff(String staffId, String companyId, Integer roleId,String staffName,String deptId,int isDeptManager) {
    this.staffId = staffId;
    this.companyId = companyId;
    this.roleId = roleId;
    this.staffName = staffName;
    this.deptId = deptId;
    this.isDeptManager = isDeptManager;
  }

  public Staff(String staffId, String phone, String password, String staffNo, int staffStatus, String deptId, String deptName, String companyId, String staffName, String helpCode, String sex, Date birthday, Integer roleId, String roleName) {
    this.staffId = staffId;
    this.phone = phone;
    this.password = password;
    this.staffNo = staffNo;
    this.staffStatus = staffStatus;
    this.deptId = deptId;
    this.deptName = deptName;
    this.companyId = companyId;
    this.staffName = staffName;
    this.helpCode = helpCode;
    this.sex = sex;
    this.birthday = birthday;
    this.roleId = roleId;
    this.roleName = roleName;
  }
}
