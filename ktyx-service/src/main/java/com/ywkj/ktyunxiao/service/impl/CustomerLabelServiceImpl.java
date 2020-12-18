package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.CustomerLabelMapper;
import com.ywkj.ktyunxiao.model.CustomerLabel;
import com.ywkj.ktyunxiao.service.CustomerLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 17:58
 */
@Service
public class CustomerLabelServiceImpl implements CustomerLabelService {

    @Autowired
    private CustomerLabelMapper customerLabelMapper;

    @Override
    public boolean insertList(List<CustomerLabel> customerLabelList,String customerId,String companyId) {
        return customerLabelMapper.insertList(customerLabelList,customerId,companyId) == customerLabelList.size();
    }

    @Override
    public boolean updateListByCustomerId(List<CustomerLabel> customerLabelList, String customerId, String companyId) {
        customerLabelMapper.deleteByCustomerId(customerId, companyId);
        return customerLabelMapper.insertList(customerLabelList, customerId, companyId) == customerLabelList.size();
    }

    @Override
    public boolean updateLabelId(List<Integer> labelIdList, int labelId, String companyId) {
        return customerLabelMapper.updateLabelId(labelIdList,labelId,companyId) == labelIdList.size();
    }
}
