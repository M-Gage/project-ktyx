package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 客户搜索
 * @author LiWenHao
 * @date 2018/6/8 18:01
 */
@Data
public class CustomerSearchPojo {

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
     * 等级数组
     */
    private String[] levelArray;
    /**
     * 标签数组
     */
    private int[] labelArray;
    /**
     * 部门Id
     */
    private String deptId;
    /**
     * 上级Id
     */
    private String chiefId;
    /**
     * 创建时间区间
     */
    private Date[] createTimeArray;
    /**
     * 下单时间区间
     */
    private Date[] orderTimeArray;
    /**
     * 跟进时间区间
     */
    private Date[] followTimeArray;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 下单次数区间
     */
    private String[] orderCountArray;
    /**
     * 跟进次数区间
     */
    private String[] followCountArray;
    /**
     * 订单金额区间
     */
    private String[] orderMoneyArray;
    /**
     *省
     */
    private String province;
    /**
     *市
     */
    private String city;
    /**
     *区
     */
    private String district;
    /**
     * 默认降序
     */
    private String sortOrder = "desc";
    /**
     * 默认列
     */
    private String sortColumn = "lastModifyTime";
    /**
     * 是否部门管理员
     */
    private int isDeptManager;
}
