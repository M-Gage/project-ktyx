package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.MapChartCityMapper;
import com.ywkj.ktyunxiao.model.MapChartCity;
import com.ywkj.ktyunxiao.model.MapChartProvince;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;
import com.ywkj.ktyunxiao.service.MapChartCityService;
import com.ywkj.ktyunxiao.service.MapChartProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapChartCityServiceImpl implements MapChartCityService {

    @Autowired
    private MapChartCityMapper mapChartCityMapper;

    @Autowired
    private MapChartProvinceService mapChartProvinceService;

    @Override
    public void insertOrUpdate(MapChartCity mapChartCity) {
        if (StringUtil.isEmpty(mapChartCity.getProvince()) && StringUtil.isEmpty(mapChartCity.getCity())) {
            throw new ParamException();
        }
        //查询是否存在
        MapChartProvince mapChartProvince = new MapChartProvince(mapChartCity.getProvince(), mapChartCity.getCompanyId(), mapChartCity.getCustomerCount(), mapChartCity.getAmountCount());
        //不存在
        if (mapChartCityMapper.selectByCompanyIdAndProvinceAndCityCount(mapChartCity.getCompanyId(),mapChartCity.getCity(),mapChartCity.getProvince()) == 0) {
            mapChartProvinceService.insertOrUpdate(mapChartProvince);
            mapChartCityMapper.insert(mapChartCity);
        } else {
            mapChartCityMapper.updateCount(mapChartCity);
            mapChartProvinceService.updateCount(mapChartProvince);
        }
    }

    @Override
    public List<MapChartPojo> selectByCompanyId(String companyId) {
        return mapChartCityMapper.selectByCompanyId(companyId);
    }
}
