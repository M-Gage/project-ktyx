package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.MapChartPojo;
import com.ywkj.ktyunxiao.service.MapChartCityService;
import com.ywkj.ktyunxiao.service.MapChartProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/13 17:03
 */
@RestController
@RequestMapping("/chart")
public class MapChartController {

    @Autowired
    private MapChartCityService mapChartCityService;

    @Autowired
    private MapChartProvinceService mapChartProvinceService;

    @GetMapping("/map")
    public JsonResult selectChartList(@SessionAttribute("staff") Staff staff){
        List<MapChartPojo> mapChartPojoList = mapChartCityService.selectByCompanyId(staff.getCompanyId());
        mapChartPojoList.addAll(mapChartProvinceService.selectByCompanyId(staff.getCompanyId()));
        return JsonResult.success(mapChartPojoList);
    }

}
