package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 职员搜索
 * @author LiWenHao
 * @date 2018/6/8 18:01
 */
@Data
public class StaffSearchPojo {

    /**
     * 页数
     */
    private int pageSize;
    /**
     * 页码
     */
    private int pageNumber;
    /**
     * 公司Id
     */
    private String companyId;
    /**
     * 职员Id
     */
    private String staffId;
    /**
     * 部门Id
     */
    private String deptId;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 性别
     */
    private String sex;
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 生日区间
     */
    private Date[] birthdayArray;
    /**
     * 创建时间区间
     */
    private Date[] createTimeArray;
    /**
     * 是否部门管理员(页面传入)
     */
    private String isAdmin;
    /**
     * 默认降序
     */
    private String sortOrder = "desc";
    /**
     * 默认列
     */
    private String sortColumn = "createTime";

    /**
     * 是否部门管理员
     */
    private int isDeptManager;
}
