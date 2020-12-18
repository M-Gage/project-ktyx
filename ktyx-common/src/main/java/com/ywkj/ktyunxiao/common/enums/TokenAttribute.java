package com.ywkj.ktyunxiao.common.enums;

import lombok.Getter;

/**
 * @author LiWenHao
 * @date 2018/07/18 15:37
 */
@Getter
public enum TokenAttribute {
    COMPANY_ID("companyId"),
    STAFF_ID("staffId"),
    ROLE_ID("roleId"),
    STAFF_NAME("staffName"),
    DEPT_ID("deptId"),
    IS_DEPT_MANAGER("isDeptManager");

    private String value;

    TokenAttribute(String value) {
        this.value = value;
    }
}
