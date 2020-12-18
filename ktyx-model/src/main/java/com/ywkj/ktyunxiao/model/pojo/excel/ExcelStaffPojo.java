package com.ywkj.ktyunxiao.model.pojo.excel;

import com.ywkj.ktyunxiao.common.annotation.Excel;
import lombok.Data;

/**
 * 职员excel导出类
 * @author LiWenHao
 * @date 2018/4/27 10:16
 */
@Data
public class ExcelStaffPojo {

    @Excel(name = "职员编号", index = 1, width = 5000)
    private String staffNo;
    @Excel(name = "职员名称", index = 2, width = 5000)
    private String staffName;
    @Excel(name = "性别", index = 3, width = 2000)
    private String sex;
    @Excel(name = "手机号", index = 4, width = 5000)
    private String phone;
    @Excel(name = "职员状态", index = 5, width = 2500)
    private String staffStatus;
    @Excel(name = "部门名称", index = 6, width = 7000)
    private String deptName;
    @Excel(name = "部门管理员", index = 7, width = 3200)
    private String isDeptManager;
    @Excel(name = "角色", index = 8, width = 3000)
    private String roleName;
    @Excel(name = "生日", index = 9, width = 3500)
    private String birthday;
    @Excel(name = "添加时间", index = 10, width = 5500)
    private String createTime;

    public void setIsDeptManager(String isDeptManager) {
        if (isDeptManager.equals("0")) {
            this.isDeptManager = "否";
        }else {
            this.isDeptManager = "是";
        }
    }

    public void setStaffStatus(String staffStatus) {
        if (staffStatus.equals("0")) {
            this.staffStatus = "正常";
        }else {
            this.staffStatus = "禁用";
        }
    }
}
