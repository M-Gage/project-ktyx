package com.ywkj.ktyunxiao.model.vo;

import com.ywkj.ktyunxiao.model.*;
import lombok.Data;

import java.util.List;

/**
 * 客户视图对象
 *
 * @author LiWenHao
 * @date 2018/6/7 15:42
 */
@Data
public class CustomerVO extends Customer {

    /**
     * 联系人列表
     */
    private List<Contact> contactList;
    /**
     * 客户标签列表
     */
    private List<CustomerLabel> customerLabelList;
    /**
     * 跟进列表
     */
    private List<Follow> followList;
    /**
     * 标签的字符串
     */
    private String labels;

}
