package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Announcement;
import com.ywkj.ktyunxiao.model.Staff;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/7/10 11:34
 */
public interface AnnouncementService {
    /**插入公告
     * @param staff
     * @param announcement
     * @return
     */
     int insertAnnouncement(Staff staff, Announcement announcement);

    /**查询公告列表
     * @param staff
     * @return
     */
    List<Announcement> selectAnnouncement(Staff staff);

    /**分页查询公告列表
     * @param staff
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Announcement> selectAnnouncementList(Staff staff, int pageNumber, Integer pageSize);

    /**查询公告总条数
     * @param staff
     * @return
     */
    Integer selectAnnouncementListCount(Staff staff);

    /**更改状态
     * @param staff
     * @param announcementId
     * @param state
     * @return
     */
    int updateAnnouncement(Staff staff, Integer announcementId, Integer state);

    /**删除公告
     * @param announcementId
     * @return
     */
    int deleteAnnouncement(Integer announcementId);

}
