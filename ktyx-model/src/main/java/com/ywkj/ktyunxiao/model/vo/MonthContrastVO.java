package com.ywkj.ktyunxiao.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 上月与上上月数据对比视图对象
 *
 * @author MiaoGuoZhu
 * @date 2018/2/26 0026 16:52
 */
@Data
public class MonthContrastVO {
    /**
     * 上月数据
     */
    private BigDecimal lastMonthCount;
    /**
     *上上月数据
     */
    private BigDecimal beforeLastMonthCount;
}
