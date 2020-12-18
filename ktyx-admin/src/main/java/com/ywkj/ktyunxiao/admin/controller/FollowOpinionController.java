package com.ywkj.ktyunxiao.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.FollowOpinionPojo;
import com.ywkj.ktyunxiao.service.FollowOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/07/09 15:56
 */
@RestController
@RequestMapping("/followOpinion")
public class FollowOpinionController {

    @Autowired
    private FollowOpinionService followOpinionService;

    @PostMapping("")
    public JsonResult insert(@SessionAttribute("staff") Staff staff, @RequestBody FollowOpinionPojo followOpinionPojo){
        followOpinionPojo.setCompanyId(staff.getCompanyId());
        followOpinionPojo.setStaffId(staff.getStaffId());
        followOpinionPojo.setStaffName(staff.getStaffName());
        followOpinionService.insert(followOpinionPojo,followOpinionPojo.getOpinion());
        return JsonResult.success();
    }

    @GetMapping("/{followId:[\\w]+}")
    public JsonResult selectByFollowId(@SessionAttribute("staff") Staff staff,@PathVariable("followId") String followId){
        return JsonResult.success(followOpinionService.selectByFollowId(staff.getCompanyId(),followId));
    }

    @DeleteMapping("/{followId:[\\w]+}")
    public JsonResult deleteByFollowId(@SessionAttribute("staff") Staff staff,
                                       @PathVariable("followId") String followId,
                                       @RequestParam int opinion,
                                       @RequestParam String followStaffId){
        if (StringUtil.isEmpty(followStaffId)) {
            throw new ParamException();
        }
        followOpinionService.deleteByFollowIdAndStaffId(staff.getCompanyId(), followId, staff.getStaffId(),followStaffId,opinion);
        return JsonResult.success();
    }


}
