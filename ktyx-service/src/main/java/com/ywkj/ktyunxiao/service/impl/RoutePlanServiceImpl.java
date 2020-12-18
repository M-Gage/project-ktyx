package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.RoutePlanMapper;
import com.ywkj.ktyunxiao.model.RoutePlan;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.RoutePlanPojo;
import com.ywkj.ktyunxiao.service.RoutePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/9 13:42
 */
@Service
public class RoutePlanServiceImpl implements RoutePlanService {

    @Autowired
    private RoutePlanMapper routePlanMapper;

    @Override
    public int insertRouteList(List<RoutePlanPojo> routePlanPojos, String routePlanDate, Staff staff) {
        return routePlanMapper.insertRouteList(routePlanPojos,routePlanDate,staff);
    }
    @Override
    public List<RoutePlan> selectAllRoutePlanByStaffId(String staffId, String companyId) {
        return routePlanMapper.selectAllRoutePlanByStaffId(staffId,companyId);
    }

    @Override
    public int updateRoutePlanState(String companyId, String staffId, Integer routePlanId) {
        return routePlanMapper.updateRoutePlanState(companyId,staffId,routePlanId);
    }

    @Override
    public RoutePlan selectRoutePlanByRoutePlanId(String companyId, Integer routePlanId) {
        return routePlanMapper.selectRoutePlanByRoutePlanId(companyId,routePlanId);
    }

    @Override
    public List<RoutePlan> selectRoutePlanByDate(String staffId, String date) {
        return routePlanMapper.selectRoutePlanByDate(staffId,date);
    }
}
