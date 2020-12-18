package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.MapChartProvince;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MapChartProvinceMapper {

    /**
     * 添加省份数据
     * @param mapChartProvince
     * @return
     */
    int insert(MapChartProvince mapChartProvince);

    /**
     * 根据公司Id省查询数量
     * @param companyId
     * @param province
     * @return
     */
    int selectByCompanyIdAndProvinceCount(@Param("companyId") String companyId, @Param("province") String province);

    /**
     * 更新数量
     * @param mapChartProvince
     * @return
     */
    int updateCount(MapChartProvince mapChartProvince);

    /**
     * 根据公司Id获取市数据
     * @param companyId
     * @return
     */
    List<MapChartPojo> selectByCompanyId (String companyId);

}
