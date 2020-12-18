package com.ywkj.ktyunxiao.common.pojo;

import lombok.Data;

/**
 * excel对象
 * @author LiWenHao
 * @date 2018/06/29 9:39
 */
@Data
public class ExcelPojo {
    private int index;
    private String name;
    private String key;
    private int width;

    public ExcelPojo() {
    }

    public ExcelPojo(int index, String name, String key, int width) {
        this.index = index;
        this.name = name;
        this.key = key;
        this.width = width;
    }

}
