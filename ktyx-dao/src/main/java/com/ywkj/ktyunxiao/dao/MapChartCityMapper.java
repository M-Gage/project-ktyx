package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.MapChartCity;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MapChartCityMapper {

    /**
     * 添加市数据
     * @param mapChartCity
     * @return
     */
    int insert(MapChartCity mapChartCity);

    /**
     * 根据公司Id省和市查询数量
     * @param companyId
     * @param city
     * @param province
     * @return
     */
    int selectByCompanyIdAndProvinceAndCityCount(@Param("companyId") String companyId, @Param("city") String city, @Param("province") String province);

    /**
     * 更新数量
     * @param mapChartCity
     * @return
     */
    int updateCount(MapChartCity mapChartCity);

    /**
     * 根据公司Id获取市数据
     * @param companyId
     * @return
     */
    List<MapChartPojo> selectByCompanyId (String companyId);
}
