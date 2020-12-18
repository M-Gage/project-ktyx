package com.ywkj.ktyunxiao.model.pojo.excel;


import com.ywkj.ktyunxiao.common.annotation.Excel;
import lombok.Data;

/**
 * 商品Excel导出对象
 * @author LiWenHao
 * @date 2018/4/27 10:16
 */
@Data
public class ExcelGoodsPojo {
    @Excel(name = "商品编号",index = 1)
    private String goodsNo;
    @Excel(name = "商品名称",index = 2)
    private String goodsName;
    @Excel(name = "条形码",index = 3)
    private String barCode;
    @Excel(name = "单位",index = 4)
    private String unit;
    @Excel(name = "零售价",index = 5)
    private String retailPrice;
    @Excel(name = "进价",index = 6)
    private String purchasePrice;
    @Excel(name = "备注",index = 7)
    private String remark;
    @Excel(name = "库存",index = 8)
    private String stock;
    @Excel(name = "库存预警值",index = 9)
    private String stockWarning;
    @Excel(name = "生产日期",index = 10)
    private String productDate;
    @Excel(name = "保质期",index = 11)
    private String expiryDate;
    @Excel(name = "分类",index = 12)
    private String typeName;
    @Excel(name = "最后修改时间",index = 13)
    private String lastModifyTime;
    @Excel(name = "标准属性",index = 14)
    private String standardAttribute;
    @Excel(name = "非标属性",index = 15)
    private String nonstandardAttribute;
    @Excel(name = "辅助属性",index = 16)
    private String assistAttribute;
}
