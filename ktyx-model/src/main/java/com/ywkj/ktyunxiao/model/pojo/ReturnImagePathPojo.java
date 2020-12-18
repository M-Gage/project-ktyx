package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

import java.util.List;

/**WangEditor图片返回格式
 * @author MiaoGuoZhu
 * @date 2018/7/10 9:39
 */
@Data
public class ReturnImagePathPojo {
    private int errno;
    private List<String> data;
}
