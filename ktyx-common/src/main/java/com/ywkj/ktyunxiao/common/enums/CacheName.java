package com.ywkj.ktyunxiao.common.enums;

import lombok.Getter;

/**
 * @author LiWenHao
 * @date 2018/07/17 9:30
 */
@Getter
public enum CacheName {

    EXPERIENCE("experienceCache"),
    TOKEN("tokenCache"),
    GRADE("gradeCache");

    private String cacheName;

    CacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
