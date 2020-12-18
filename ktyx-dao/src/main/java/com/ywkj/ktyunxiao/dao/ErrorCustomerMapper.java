package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Customer;
import com.ywkj.ktyunxiao.model.ErrorCustomer;
import com.ywkj.ktyunxiao.model.Staff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/06/27 13:52
 */
@Component
public interface ErrorCustomerMapper {
    /**
     * 添加错误客户
     * @param errorCustomer
     * @return
     */
    int insert(ErrorCustomer errorCustomer);

    /**
     * 根据经纬度范围获取
     * @param longitudeArray
     * @param latitudeArray
     * @param staff
     * @return
     */
    List<ErrorCustomer> selectLonAndLatBetween(@Param("longitudeArray") String[] longitudeArray,@Param("latitudeArray") String[] latitudeArray,  @Param("pojo") Staff staff);

}
