package com.ywkj.ktyunxiao.model.pojo;

import com.ywkj.ktyunxiao.model.Staff;
import lombok.Data;

/**
 * 员工视图对象
 *
 * @author LiWenHao
 * @date 2018/5/23 14:07
 */
@Data
public class StaffPojo extends Staff {

    /**
     * token字符串
     */
    private String token;
}
