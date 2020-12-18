package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.CustomerLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 17:59
 */
@Component
public interface CustomerLabelMapper {

    /**
     * 批量添加客户标签
     * @param customerLabelList
     * @return
     */
    int insertList(@Param("customerLabelList") List<CustomerLabel> customerLabelList, @Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 根据客户Id删除标签
     * @param customerId
     * @param companyId
     * @return
     */
    void deleteByCustomerId(@Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 批量修改客户标签
     * @param labelIdList
     * @param labelId
     * @param companyId
     * @return
     */
    int updateLabelId(@Param("labelIdList") List<Integer> labelIdList, @Param("labelId") int labelId, @Param("companyId") String companyId);
}
