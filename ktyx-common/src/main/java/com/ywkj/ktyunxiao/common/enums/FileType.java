package com.ywkj.ktyunxiao.common.enums;

import lombok.Getter;

/**
 * 文件类型枚举
 * @author LiWenHao
 * @date 2018/1/19 15:08
 */
@Getter
public enum FileType {

    /**
     * 文件类型后缀
     */
    MP3("mp3"), PNG("png"), JPG("jpg"), XLS("xls"), XLSX("xlsx");

    private String name;

    FileType(String name) {
        this.name = name;
    }

    public static FileType val(String fileType){
        for (FileType value: values()){
            if (fileType.equals(value.getName())) {
                return value;
            }
        }
        return null;
    }

}
