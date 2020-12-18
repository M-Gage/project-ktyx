package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Schedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/8 11:15
 */
@Component
public interface ScheduleMapper {

    /**
     * 插入日程
     *
     * @param schedule
     * @return
     */
    int insertSchedule(Schedule schedule);

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
     * @param eventName
     * @param staffId
     * @param companyId
     * @return
     */
    int insertScheduleEvent(@Param("eventName") String eventName,
                            @Param("staffId") String staffId,
                            @Param("companyId") String companyId);

    /**
     * 查询员工当天日程
     *
     * @param customerId
     * @param staffId
     * @return
     */
    List<Schedule> selectTodayByCompanyAndCustomerId(@Param("companyId") String customerId,
                                                     @Param("staffId") String staffId);

    /**
     * 查询日程便签
     *
     * @param staffId
     * @param companyId
     * @return
     */
    List<String> selectScheduleEvent(@Param("staffId") String staffId,
                                     @Param("companyId") String companyId);

    /**
     * 删除日程便签
     *
     * @param eventName
     * @param staffId
     * @param companyId
     * @return
     */
    int deleteScheduleEvent(@Param("eventName") String eventName,
                            @Param("staffId") String staffId,
                            @Param("companyId") String companyId);


    /**
     * 查询员工全部日程
     *
     * @param companyId
     * @param staffId
     * @return
     */
    List<Schedule> selectAllScheduleByStaffId(@Param("companyId") String companyId, @Param("staffId") String staffId);


    /**
     * 根据日程id查询日程详情
     *
     * @param companyId
     * @param scheduleId
     * @return
     */
    Schedule selectScheduleByScheduleId(@Param("companyId") String companyId, @Param("scheduleId") Integer scheduleId);

    /**
     * 查询日程日期
     *
     * @param companyId
     * @param staffId
     * @return
     */
    List<String> selectAllScheduleDate(@Param("companyId") String companyId, @Param("staffId") String staffId);

    /**
     * 查询某天日程
     *
     * @param staffId
     * @param date
     * @return
     */
    List<Schedule> selectScheduleByDate(@Param("staffId") String staffId, @Param("date") String date);
}
