package com.ywkj.ktyunxiao.model.pojo;

import com.ywkj.ktyunxiao.model.vo.CustomerVO;
import lombok.Data;

/**
 * 客户对象
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
public class CustomerPojo extends CustomerVO {

    /**
     * 删除联系人id数组
     */
    private String[] deleteContactIdArray;

}
