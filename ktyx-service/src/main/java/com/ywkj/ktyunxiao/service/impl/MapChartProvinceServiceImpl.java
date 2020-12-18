package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.dao.MapChartProvinceMapper;
import com.ywkj.ktyunxiao.model.MapChartProvince;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;
import com.ywkj.ktyunxiao.service.MapChartProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapChartProvinceServiceImpl implements MapChartProvinceService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private MapChartProvinceMapper mapChartProvinceMapper;

    @Override
    public void insertOrUpdate(MapChartProvince mapChartProvince) {
        //不存在
        if (mapChartProvinceMapper.selectByCompanyIdAndProvinceCount(mapChartProvince.getCompanyId(),mapChartProvince.getProvince()) == 0) {
            mapChartProvinceMapper.insert(mapChartProvince);
        } else {
            updateCount(mapChartProvince);
        }
    }

    @Override
    public boolean updateCount(MapChartProvince mapChartProvince) {
        return mapChartProvinceMapper.updateCount(mapChartProvince) == SUCCESS_INT;
    }

    @Override
    public List<MapChartPojo> selectByCompanyId(String companyId) {
        return mapChartProvinceMapper.selectByCompanyId(companyId);
    }
}
