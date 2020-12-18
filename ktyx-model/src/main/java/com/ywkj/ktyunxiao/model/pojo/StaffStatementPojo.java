package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/25 14:34
 */
@Data
public class StaffStatementPojo extends StatementSearchBase{

    /**
     * 是否按月显示
     */
    private String isMonth;


    public Boolean isMonth() {
        return "true".equalsIgnoreCase(isMonth);
    }
}
