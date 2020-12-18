package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Announcement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/7/10 11:36
 */
@Component
public interface AnnouncementMapper {

    /**
     * 插入公告
     *
     * @param announcement
     * @return
     */
    int insertAnnouncement(Announcement announcement);

    /**
     * 查询公告标题
     *
     * @param companyId
     * @return
     */
    List<Announcement> selectAnnouncement(String companyId);

    /**
     * 查询公告列表
     *
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Announcement> selectAnnouncementList(@Param("companyId") String companyId, @Param("pageNumber") int pageNumber, @Param("pageSize") Integer pageSize);

    /**
     * 公告条数
     *
     * @param companyId
     * @return
     */
    Integer selectAnnouncementListCount(String companyId);

    /**
     * 修改状态
     *
     * @param companyId
     * @param announcementId
     * @param state
     * @return
     */
    int updateAnnouncementState(@Param("companyId") String companyId, @Param("announcementId") Integer announcementId, @Param("state") Integer state);

    /**
     * 删除公告
     *
     * @param announcementId
     * @return
     */
    int deleteAnnouncement(@Param("announcementId") Integer announcementId);
}
