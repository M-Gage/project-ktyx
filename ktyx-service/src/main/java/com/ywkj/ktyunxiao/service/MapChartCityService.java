package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.MapChartCity;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;

import java.util.List;

public interface MapChartCityService {

    void insertOrUpdate(MapChartCity mapChartCity);

    List<MapChartPojo> selectByCompanyId (String companyId);
}
