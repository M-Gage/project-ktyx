package com.ywkj.ktyunxiao.common.enums;

import lombok.Getter;

/**
 * @author LiWenHao
 * @date 2018/07/16 15:54
 */
@Getter
public enum FollowOpinionType {
    LIKE("点赞"),
    DIS_LIKE("踩");

    private String name;

    FollowOpinionType(String name) {
        this.name = name;
    }
}
