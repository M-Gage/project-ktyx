package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.ErrorCustomer;
import com.ywkj.ktyunxiao.model.Staff;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/06/27 13:51
 */
public interface ErrorCustomerService {

    boolean insert(ErrorCustomer errorCustomer);

    List<ErrorCustomer> selectLonAndLatBetween(String[] longitudeArray,String[] LatitudeArray, Staff staff);

}
