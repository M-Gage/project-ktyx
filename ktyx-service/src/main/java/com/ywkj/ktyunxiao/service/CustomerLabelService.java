package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Customer;
import com.ywkj.ktyunxiao.model.CustomerLabel;
import com.ywkj.ktyunxiao.model.Label;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 17:57
 */
public interface CustomerLabelService {

    boolean insertList(List<CustomerLabel> customerLabelList,String customerId, String companyId);

    boolean updateListByCustomerId(List<CustomerLabel> customerLabelList, String customerId, String companyId);

    boolean updateLabelId(List<Integer> labelIdList, int labelId, String companyId);

}
