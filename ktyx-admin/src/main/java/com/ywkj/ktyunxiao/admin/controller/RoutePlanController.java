package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.CustomerMapper;
import com.ywkj.ktyunxiao.model.RoutePlan;
import com.ywkj.ktyunxiao.model.Schedule;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.RoutePlanPojo;
import com.ywkj.ktyunxiao.service.RoutePlanService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**路线规划控制器
 * @author MiaoGuoZhu
 * @date 2018/6/8 10:42
 */
@RestController
@RequestMapping("/route")
public class RoutePlanController {
    @Autowired
    private RoutePlanService routePlanService;


    /**
     * 根据选择的职员获取所有的规划
     *
     * @param staff
     * @param staffId
     * @return
     */
    @GetMapping("")
    public JsonResult selectAllRoutePlanByStaffId(@SessionAttribute("staff") Staff staff
            , @RequestParam(value = "staffId", required = false) String staffId) {
        return JsonResult.success(routePlanService.selectAllRoutePlanByStaffId(StringUtil.isNotEmpty(staffId) ? staffId : staff.getStaffId(),
                staff.getCompanyId()));
    }


    /**
     * 根据日程ID获取规划详情
     *
     * @param staff
     * @param routePlanId-
     * @return
     */
    @GetMapping("/detail")
    public JsonResult selectProgrammeByScheduleId(@SessionAttribute("staff") Staff staff,
                                                  Integer routePlanId) {
        RoutePlan routePlan = routePlanService.selectRoutePlanByRoutePlanId(staff.getCompanyId(), routePlanId);
        return JsonResult.success(routePlan);
    }
}
