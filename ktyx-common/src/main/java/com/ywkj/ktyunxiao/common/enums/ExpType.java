package com.ywkj.ktyunxiao.common.enums;

import lombok.Getter;

/**
 * @author LiWenHao
 * @date 2018/07/05 11:49
 */
@Getter
public enum ExpType {
    INSERT_CUSTOMER("添加客户"),
    INSERT_ORDER("报计划"),
    INSERT_FOLLOW("添加跟进"),
    INSERT_FOLLOW_OPINION("跟进被%s"),
    DELETE_FOLLOW_OPINION("跟进%s被取消");

    private String name;

    ExpType(String name) {
        this.name = name;
    }
}
