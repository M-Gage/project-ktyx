package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Company;
import org.apache.ibatis.annotations.Param;

public interface CompanyMapper {

    /**
     * 根据公司id获取关键字
     * @param companyId
     * @return
     */
    Company selectMapKeyWordByPrimary(String companyId);

    /**
     * 修改poi和关键字
     * @param poi
     * @param keyWord
     * @param companyId
     * @return
     */
    int updateMapKeyWord(@Param("poi") String poi, @Param("keyWord") String keyWord, @Param("companyId") String companyId);
}
