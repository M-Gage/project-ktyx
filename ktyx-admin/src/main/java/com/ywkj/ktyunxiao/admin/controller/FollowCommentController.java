package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.FollowComment;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.FollowCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/07/10 17:16
 */
@RestController
@RequestMapping("/followComment")
public class FollowCommentController {

    @Autowired
    private FollowCommentService followCommentService;

    @PostMapping("")
    public JsonResult insert(@SessionAttribute("staff") Staff staff, @RequestBody FollowComment followComment){
        followComment.setCompanyId(staff.getCompanyId());
        followComment.setStaffId(staff.getStaffId());
        followComment.setStaffName(staff.getStaffName());
        if (!followCommentService.insert(followComment)) {
            return JsonResult.error(Code.INSERT_ERROR);
        }
        return JsonResult.success();
    }

    @GetMapping("/{followId:[\\w]+}")
    public JsonResult selectByFollowId(@SessionAttribute("staff") Staff staff,@PathVariable("followId") String followId){
        return JsonResult.success(followCommentService.selectByFollowId(staff.getCompanyId(),followId));
    }

}

