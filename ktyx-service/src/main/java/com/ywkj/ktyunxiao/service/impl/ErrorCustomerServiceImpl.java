package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.dao.ErrorCustomerMapper;
import com.ywkj.ktyunxiao.model.ErrorCustomer;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ErrorCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/06/27 13:52
 */
@Service
public class ErrorCustomerServiceImpl implements ErrorCustomerService {

    //成功条数
    private int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private ErrorCustomerMapper errorCustomerMapper;

    @Override
    public boolean insert(ErrorCustomer errorCustomer) {

        return errorCustomerMapper.insert(errorCustomer) == SUCCESS_INT;
    }

    @Override
    public List<ErrorCustomer> selectLonAndLatBetween(String[] longitudeArray, String[] LatitudeArray, Staff staff) {
        return errorCustomerMapper.selectLonAndLatBetween(longitudeArray,LatitudeArray,staff);
    }
}
