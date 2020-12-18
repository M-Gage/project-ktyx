package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.dao.CustomerMapper;
import com.ywkj.ktyunxiao.model.Schedule;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日程控制器
 *
 * @author MiaoGuoZhu
 * @date 2018/6/8 10:27
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {


    @Autowired
    private ScheduleService scheduleService;

    /**
     * 添加日程
     *
     * @param schedule
     * @param staff
     * @return
     */
    @PostMapping("")
    public JsonResult insertSchedule(@RequestBody Schedule schedule, @SessionAttribute("staff") Staff staff) {
        scheduleService.insertSchedule(schedule, staff);
        return schedule.getScheduleId() != null ? JsonResult.success(schedule) : JsonResult.error(Code.INSERT_ERROR);
    }


    /**
     * 修改日程
     *
     * @param schedule
     * @return
     */
    @PutMapping("")
    public JsonResult updateSchedule(@RequestBody Schedule schedule) {
        return scheduleService.updateSchedule(schedule) > 0 ? JsonResult.success() : JsonResult.failed();
    }

    /**
     * 删除日程
     *
     * @param scheduleId
     * @return
     */
    @DeleteMapping("/{scheduleId:[\\d]+}")
    public JsonResult deleteSchedule(@PathVariable(value = "scheduleId") Integer scheduleId) {
        return scheduleService.deleteSchedule(scheduleId) > 0 ? JsonResult.success() : JsonResult.failed();
    }

    /**
     * 添加便捷日程
     *
     * @param event
     * @param staff
     * @return
     */
    @PostMapping("/{event}")
    public JsonResult insertScheduleEvent(@PathVariable(value = "event") String event,
                                          @SessionAttribute("staff") Staff staff) {
        return scheduleService.insertScheduleEvent(event, staff.getStaffId(), staff.getCompanyId()) > 0 ? JsonResult.success() : JsonResult.failed();
    }

    /**
     * 获取当天日程
     *
     * @param customerId
     * @param staff
     * @return
     */
    @GetMapping("/today/{customerId:[\\d]+}")
    public JsonResult selectTodayScheduleByCustomerId(@PathVariable("customerId") String customerId,
                                                      @SessionAttribute("staff") Staff staff) {
        List<Schedule> scheduleList = scheduleService.selectTodayByCompanyAndCustomerId(customerId, staff.getStaffId());
        return scheduleList != null && scheduleList.size() > 0 ? JsonResult.success(scheduleList) : JsonResult.failed();
    }

    /**
     * 获取便捷日程
     *
     * @param staff
     * @return
     */
    @GetMapping("/event")
    public JsonResult selectScheduleEvent(@SessionAttribute("staff") Staff staff) {
        List<String> stringList = scheduleService.selectScheduleEvent(staff.getStaffId(), staff.getCompanyId());
        return stringList != null && stringList.size() > 0 ? JsonResult.success(stringList) : JsonResult.failed();
    }

    /**
     * 删除便捷日程
     *
     * @param eventName
     * @param staff
     * @return
     */
    @DeleteMapping("/event/{eventName}")
    public JsonResult deleteScheduleEvent(@PathVariable("eventName") String eventName, @SessionAttribute("staff") Staff staff) {
        return scheduleService.deleteScheduleEvent(eventName, staff.getStaffId(), staff.getCompanyId()) > 0 ? JsonResult.success() : JsonResult.failed();

    }

    /**
     * 根据员工ID获取所有的日程
     *
     * @param staff
     * @return
     */
    @GetMapping("/list")
    public JsonResult selectAllScheduleByCompany(@SessionAttribute("staff") Staff staff,
                                                 @RequestParam(value = "staffId", required = false) String staffId) {
        List<Schedule> schedules = scheduleService.selectAllScheduleByStaffId(staff.getCompanyId(), staffId == null ? staff.getStaffId() : staffId);
        return JsonResult.success(schedules);
    }


    /**
     * 根据日程ID获取日程详情
     *
     * @param staff
     * @return
     */
    @GetMapping("/detail")
    public JsonResult selectScheduleByScheduleId(@SessionAttribute("staff") Staff staff, Integer scheduleId) {
        return JsonResult.success(scheduleService.selectScheduleByScheduleId(staff.getCompanyId(), scheduleId));
    }


}
