package com.ywkj.ktyunxiao.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 职员一览表表格数据视图对象
 * @author MiaoGuoZhu
 * @date 2018/2/27 0027 14:42
 */
@Data
public class StaffTableLookUpVO {
    /**
     * 月数据
     */
    private BigDecimal monthData;
    /**
     * 季数据
     */
    private BigDecimal quarterData;
    /**
     *年数据
     */
    private BigDecimal yearData;
    /**
     *历史数据
     */
    private BigDecimal historyData;

}
