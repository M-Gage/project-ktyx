package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Schedule;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * app日程控制器
 *
 * @author MiaoGuoZhu
 * @date 2018/1/8 10:26
 */
@RestController(value = "apiSchedule")
@RequestMapping("/api/schedule")
@Api(description = "日程")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @PostMapping("")
    @ApiOperation(value = "添加日程")
    public JsonResult insertSchedule(@RequestBody Schedule schedule,@ApiIgnore @RequestAttribute("staff") Staff staff) {
        scheduleService.insertSchedule(schedule, staff);
        return schedule.getScheduleId() != null ? JsonResult.success(schedule) : JsonResult.error(Code.INSERT_ERROR);
    }

    @DeleteMapping("/{scheduleId:[\\d]+}")
    @ApiOperation(value = "删除日程")
    public JsonResult deleteSchedule(@PathVariable(value = "scheduleId") Integer scheduleId) {
        return scheduleService.deleteSchedule(scheduleId) > 0 ? JsonResult.success() : JsonResult.failed();
    }

    @GetMapping("/dateList")
    @ApiOperation(value = "根据登录职员获取所有的日程的日期")
    public JsonResult selectAllScheduleDateByStaffId(@ApiIgnore @RequestAttribute("staff") Staff staff) {
        List<String> stringList = scheduleService.selectAllScheduleDate(staff.getCompanyId(),staff.getStaffId());
        return JsonResult.success(stringList);
    }

    @GetMapping("/dateInfo")
    @ApiOperation(value = "指定日期获取当前登录职员所有的日程")
    public JsonResult selectScheduleByDate(@RequestParam(value = "date") String date,
                                           @ApiIgnore @RequestAttribute("staff") Staff staff) {
        List<Schedule> scheduleList = scheduleService.selectScheduleByDate(staff.getStaffId(), date);
        return JsonResult.success(scheduleList);
    }

    @ApiOperation(value = "根据日程ID获取日程详情")
    @GetMapping("/detail")
    public JsonResult selectScheduleByScheduleId(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                                 @RequestParam(value = "scheduleId")Integer scheduleId) {
        return JsonResult.success(scheduleService.selectScheduleByScheduleId(staff.getCompanyId(), scheduleId));
    }

}
