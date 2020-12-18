package com.ywkj.ktyunxiao.model.pojo;

import com.ywkj.ktyunxiao.common.utils.StringUtil;
import lombok.Data;

/**
 * 报表搜索基础类
 * @author MiaoGuoZhu
 * @date 2018/6/28 10:25
 */
@Data
public class StatementSearchBase {
    /**
     * 公司id
     */
    private String companyId;
    /**
     * 职员id
     */
    private String staffId;


    /**
     * 日期区间
     */
    private String dateInterval;
    /**
     * 开始日期（传日期区间后自动获得，下同）
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 是否按天显示
     */
    private String isDay;

    public String getStartDate() {
        if (startDate == null) {
            if (StringUtil.isNotEmpty(dateInterval)) {
                dateInterval = dateInterval.replace(".", "-");
                startDate = dateInterval.substring(0, 10);
            }
        }
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        if (endDate == null) {
            if (StringUtil.isNotEmpty(dateInterval)) {
                dateInterval = dateInterval.replace(".", "-");
                endDate = dateInterval.substring(dateInterval.length() - 10);
            }
        }
        return endDate;
    }
    public Boolean isDay() {
        return "true".equalsIgnoreCase(isDay);
    }
}
