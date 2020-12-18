package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Schedule;
import com.ywkj.ktyunxiao.model.Staff;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/8 11:07
 */
public interface ScheduleService {

    /**
     * 插入日程
     *
     * @param schedule
     * @param staff
     * @return
     */
    int insertSchedule(Schedule schedule, Staff staff);

    /**
     * 更新日程
     *
     * @param schedule
     * @return
     */
    int updateSchedule(Schedule schedule);

    /**
     * 删除日程
     *
     * @param scheduleId
     * @return
     */
    int deleteSchedule(Integer scheduleId);

    /**
     * 插入日程便签
     *
     * @param event
     * @param staffId
     * @param companyId
     * @return
     */
    int insertScheduleEvent(String event, String staffId, String companyId);

    /**
     * 查询员工当天日程
     *
     * @param customerId
     * @param staffId
     * @return
     */
    List<Schedule> selectTodayByCompanyAndCustomerId(String customerId, String staffId);

    /**
     * 查询日程便签
     *
     * @param staffId
     * @param companyId
     * @return
     */
    List<String> selectScheduleEvent(String staffId, String companyId);

    /**
     * 删除日程便签
     *
     * @param eventName
     * @param staffId
     * @param companyId
     * @return
     */
    int deleteScheduleEvent(String eventName, String staffId, String companyId);


    /**
     * 查询员工全部日程
     *
     * @param companyId
     * @param staffId
     * @return
     */
    List<Schedule> selectAllScheduleByStaffId(String companyId, String staffId);


    /**
     * 根据日程id查询日程详情
     *
     * @param companyId
     * @param scheduleId
     * @return
     */
    Schedule selectScheduleByScheduleId(String companyId, Integer scheduleId);

    /**
     * 查询员工所有日程的日期
     *
     * @param companyId
     * @param staffId
     * @return
     */
    List<String> selectAllScheduleDate(String companyId, String staffId);

    /**
     * 根据日期查询日程
     *
     * @param staffId
     * @param date
     * @return
     */
    List<Schedule> selectScheduleByDate(String staffId, String date);
}
