package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.ScheduleMapper;
import com.ywkj.ktyunxiao.model.Schedule;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/8 11:13
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;


    @Override
    public int insertSchedule(Schedule schedule,Staff staff) {
        //设置创建人信息
        if (schedule.getStaffId() == null) {
            schedule.setStaffId(staff.getStaffId());
            schedule.setStaffName(staff.getStaffName());
            schedule.setCompanyId(staff.getCompanyId());
        }
        return scheduleMapper.insertSchedule(schedule);
    }

    @Override
    public int updateSchedule(Schedule schedule) {
        return scheduleMapper.updateSchedule(schedule);
    }

    @Override
    public int deleteSchedule(Integer scheduleId) {
        return scheduleMapper.deleteSchedule(scheduleId);
    }

    @Override
    public int insertScheduleEvent(String eventName, String staffId, String companyId) {
        return scheduleMapper.insertScheduleEvent(eventName,staffId,companyId);
    }

    @Override
    public int deleteScheduleEvent(String eventName, String staffId, String companyId) {
        return scheduleMapper.deleteScheduleEvent(eventName,staffId,companyId);
    }

    @Override
    public List<String> selectScheduleEvent(String staffId, String companyId) {
        return scheduleMapper.selectScheduleEvent(staffId,companyId);
    }

    @Override
    public List<Schedule> selectTodayByCompanyAndCustomerId(String customerId, String staffId) {
        return null;
    }

    @Override
    public List<Schedule> selectAllScheduleByStaffId(String companyId, String staffId) {
        return scheduleMapper.selectAllScheduleByStaffId(companyId,staffId);
    }

    @Override
    public Schedule selectScheduleByScheduleId(String companyId, Integer scheduleId) {
        return scheduleMapper.selectScheduleByScheduleId(companyId,scheduleId);
    }

    @Override
    public List<String> selectAllScheduleDate(String companyId, String staffId) {
        return scheduleMapper.selectAllScheduleDate(companyId,staffId);
    }

    @Override
    public List<Schedule> selectScheduleByDate(String staffId, String date) {
        return scheduleMapper.selectScheduleByDate(staffId,date);
    }
}
