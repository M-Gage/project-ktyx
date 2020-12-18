package com.ywkj.ktyunxiao.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 省地图模型
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
public class MapChartProvince {

    /**
     *省id
     */
    private Integer provinceId;
    /**
     *省
     */
    private String province;
    /**
     *公司id
     */
    private String companyId;
    /**
     *客户数量
     */
    private int customerCount;
    /**
     *总金额
     */
    private BigDecimal amountCount;

    public MapChartProvince() {
    }


    public MapChartProvince(String province, String companyId, int customerCount, BigDecimal amountCount) {
        this.province = province;
        this.companyId = companyId;
        this.customerCount = customerCount;
        this.amountCount = amountCount;
    }
}
