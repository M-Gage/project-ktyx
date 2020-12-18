package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.AnnouncementMapper;
import com.ywkj.ktyunxiao.model.Announcement;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/7/10 11:35
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {


    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public int insertAnnouncement(Staff staff,Announcement a) {
        a.setCompanyId(staff.getCompanyId());
        return announcementMapper.insertAnnouncement(a);
    }

    @Override
    public List<Announcement> selectAnnouncement(Staff staff) {
        return announcementMapper.selectAnnouncement(staff.getCompanyId());
    }

    @Override
    public List<Announcement> selectAnnouncementList(Staff staff, int pageNumber, Integer pageSize) {
        return announcementMapper.selectAnnouncementList(staff.getCompanyId(),pageNumber,pageSize);
    }

    @Override
    public Integer selectAnnouncementListCount(Staff staff) {
        return announcementMapper.selectAnnouncementListCount(staff.getCompanyId());
    }

    @Override
    public int updateAnnouncement(Staff staff, Integer announcementId, Integer state) {
        return announcementMapper.updateAnnouncementState(staff.getCompanyId(),announcementId,state);
    }

    @Override
    public int deleteAnnouncement(Integer announcementId) {
        return announcementMapper.deleteAnnouncement(announcementId);
    }
}
