package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.MapChartProvince;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;

import java.util.List;

public interface MapChartProvinceService {

    void insertOrUpdate (MapChartProvince mapChartProvince);

    boolean updateCount(MapChartProvince mapChartProvince);

    List<MapChartPojo> selectByCompanyId (String companyId);
}
