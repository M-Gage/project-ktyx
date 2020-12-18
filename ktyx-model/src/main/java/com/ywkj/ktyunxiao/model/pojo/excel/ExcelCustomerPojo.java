package com.ywkj.ktyunxiao.model.pojo.excel;

import com.ywkj.ktyunxiao.common.annotation.Excel;
import lombok.Data;

/**
 * 客户excel导出对象
 * @author LiWenHao
 * @date 2018/4/27 10:16
 */
@Data
public class ExcelCustomerPojo {

    @Excel(name = "客户编号", index = 1,width = 4500)
    private String customerNo;
    @Excel(name = "客户名称", index = 2,width = 8000)
    private String customerName;
    @Excel(name = "归属业务员", index = 3)
    private String staffName;
    @Excel(name = "创建时间", index = 4, width = 5500)
    private String createTime;
    @Excel(name = "客户等级", index = 5)
    private String level;
    @Excel(name = "客户标签", index = 6,width = 10000)
    private String labels;
    @Excel(name = "主要联系人名称", index = 7,width = 4500)
    private String contactName;
    @Excel(name = "主要联系人电话", index = 8)
    private String contactPhone;
    @Excel(name = "省", index = 9)
    private String province;
    @Excel(name = "市", index = 10)
    private String city;
    @Excel(name = "县", index = 11)
    private String district;
    @Excel(name = "详细地址", index = 12,width = 15000)
    private String detailAddress;
    @Excel(name = "跟进次数", index = 13)
    private String followCount;
    @Excel(name = "最后跟进时间", index = 14, width = 5500)
    private String lastFollowTime;
    @Excel(name = "订单数量", index = 15)
    private String orderCount;
    @Excel(name = "最后下单时间", index = 16, width = 5500)
    private String lastOrderTime;
    @Excel(name = "备注", index = 17,width = 5000)
    private String remark;
    @Excel(name = "客户状态", index = 18)
    private String customerStatus;

    public void setCustomerStatus(String customerStatus) {
        if (customerStatus.equals("0")) {
            this.customerStatus = "正常";
        }else {
            this.customerStatus = "禁用";
        }

    }
}
