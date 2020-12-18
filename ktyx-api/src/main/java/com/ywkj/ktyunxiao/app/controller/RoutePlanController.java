package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.RoutePlan;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.RoutePlanPojo;
import com.ywkj.ktyunxiao.service.RoutePlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/8 10:42
 */
@Api(description = "路程规划")
@RestController(value = "apiRoute")
@RequestMapping(value = "/api/route")
public class RoutePlanController {
    @Autowired
    private RoutePlanService routePlanService;


    @ApiOperation(value = "添加路线规划")
    @PostMapping("{routePlanDate}")
    public JsonResult insertRoutePlanList(@RequestBody List<RoutePlanPojo> routePlanPojos
            , @PathVariable(value = "routePlanDate") String routePlanDate
            ,@ApiIgnore @RequestAttribute("staff") Staff staff) {
        return routePlanService.insertRouteList(routePlanPojos, routePlanDate, staff) > 0 ? JsonResult.success() : JsonResult.failed();
    }


    @ApiOperation(value = "完成规划")
    @PutMapping("/complete/{routePlanId}")
    public JsonResult updateRoutePlanState(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                          @PathVariable(value = "routePlanId") Integer routePlanId) {
        return routePlanService.updateRoutePlanState(staff.getCompanyId(), staff.getStaffId(), routePlanId) > 0 ? JsonResult.success() : JsonResult.failed();
    }


    @ApiOperation(value = "根据路线ID获取规划详情")
    @GetMapping("/detail")
    public JsonResult selectRoutePlanById(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                                  Integer routePlanId) {
        RoutePlan routePlan = routePlanService.selectRoutePlanByRoutePlanId(staff.getCompanyId(), routePlanId);
        return JsonResult.success(routePlan);
    }

    @GetMapping("/date")
    @ApiOperation(value = "指定日期获取当前登录职员所有的规划")
    public JsonResult selectAllScheduleDateByStaffIds(@RequestParam(value = "date") String date,
                                                      @ApiIgnore @RequestAttribute("staff") Staff staff) {
            List<RoutePlan> routePlans = routePlanService.selectRoutePlanByDate(staff.getStaffId(), date);
            return JsonResult.success(routePlans);
    }
}
