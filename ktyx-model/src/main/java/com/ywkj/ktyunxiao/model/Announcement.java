package com.ywkj.ktyunxiao.model;

import lombok.Data;

/**公告模型
 * @author MiaoGuoZhu
 * @date 2018/7/10 9:03
 */
@Data
public class Announcement {
    /**
     * 公告id
     */
    private int announcementId;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 公司id
     */
    private String companyId;
    /**
     * 标题
     */
    private String title;
    /**
     * 状态
     */
    private int state;


}
