package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.utils.FileUtil;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Announcement;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.ReturnImagePathPojo;
import com.ywkj.ktyunxiao.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公告控制器
 *
 * @author MiaoGuoZhu
 * @date 2018/7/10 9:21
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping("/img")
    public ReturnImagePathPojo uploadImage(MultipartFile[] img,HttpServletRequest request) {

        ReturnImagePathPojo r = new ReturnImagePathPojo();
        List<String> p = new ArrayList<>();
        if (img!=null&&img.length>0) {
            List<Map<String, Object>> maps = FileUtil.batchFileUpload(img);
            maps.forEach(s ->{if((boolean)s.get("success")){p.add("/resource/"+s.get("newFileName").toString());} } );
        }
        r.setErrno(p.size()>0?0:1);
        r.setData(p);
        return r;
    }

    @PostMapping("")
    public JsonResult insertAnnouncement(@SessionAttribute("staff") Staff staff, @RequestBody()Announcement announcement) {
        announcementService.insertAnnouncement(staff,announcement);
        return JsonResult.success();
    }

    @GetMapping("")
    public JsonResult selectAnnouncement(@SessionAttribute("staff") Staff staff) {
        List<Announcement> announcements = announcementService.selectAnnouncement(staff);
        return JsonResult.success(announcements);
    }

    @GetMapping("/table")
    public JsonResult selectAnnouncementList(@SessionAttribute("staff") Staff staff,
                                         @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                         @RequestParam(value = "pageNumber",required = false)Integer pageNumber) {
        Map<String, Object> map = new HashMap<>(SystemConfig.MAP_INIT_SIZE);
        /*每页内容*/
        map.put("rows",announcementService.selectAnnouncementList(staff,(pageNumber-1)*pageSize,pageSize));
        /*总页数*/
        map.put("total", announcementService.selectAnnouncementListCount(staff));
        return JsonResult.success(map);
    }
    @GetMapping("/update")
    public JsonResult updateAnnouncement(@SessionAttribute("staff") Staff staff,
                                         @RequestParam(value = "announcementId") int announcementId,
                                         @RequestParam(value = "state") int state) {
        int i = announcementService.updateAnnouncement(staff, announcementId, state);
        return i>0?JsonResult.success(i):JsonResult.failed();
    }
    @DeleteMapping("/{announcementId}")
    public JsonResult deleteAnnouncement( @PathVariable(value = "announcementId") Integer announcementId) {
        int i = announcementService.deleteAnnouncement(announcementId);
        return i>0?JsonResult.success(i):JsonResult.failed();
    }



}
