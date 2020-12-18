package com.ywkj.ktyunxiao.common.enums;

import lombok.Getter;

/**
 * @author LiWenHao
 * @date 2018/07/17 11:35
 */
@Getter
public enum GradeName {
    LV1("LV1"),
    LV2("LV2"),
    LV3("LV3"),
    LV4("LV4"),
    LV5("LV5"),
    LV6("LV6"),
    LV7("LV7"),
    LV8("LV8"),
    LV9("LV9"),
    LV10("LV10"),
    LV11("LV11"),
    LV12("LV12"),
    LV13("LV13"),
    LV14("LV14"),
    LV15("LV15"),
    LV16("LV16"),
    LV17("LV17");

    private String gradeName;

    GradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
