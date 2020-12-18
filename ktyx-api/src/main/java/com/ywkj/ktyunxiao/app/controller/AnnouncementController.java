package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Announcement;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.AnnouncementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/7/14 10:57
 */
@RestController("apiAnnouncement")
@RequestMapping("/api/announcement")
@Api(description = "公告")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @ApiOperation(value = "获取公告列表")
    @GetMapping("")
    public JsonResult selectAnnouncement(@RequestAttribute("staff") Staff staff) {
        List<Announcement> announcements = announcementService.selectAnnouncement(staff);
        return JsonResult.success(announcements);
    }

}
