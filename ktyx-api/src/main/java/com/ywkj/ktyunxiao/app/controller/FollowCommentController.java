package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.FollowComment;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.FollowCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/07/10 17:16
 */
@Api(description = "跟进回复")
@RestController("apiFollowComment")
@RequestMapping("/api/followComment")
public class FollowCommentController {

    @Autowired
    private FollowCommentService followCommentService;

    @ApiOperation(value = "添加跟进回复")
    @PostMapping("")
    public JsonResult insert(@ApiIgnore @RequestAttribute("staff") Staff staff, @RequestBody FollowComment followComment){
        followComment.setCompanyId(staff.getCompanyId());
        followComment.setStaffId(staff.getStaffId());
        followComment.setStaffName(staff.getStaffName());
        if (!followCommentService.insert(followComment)) {
            return JsonResult.error(Code.INSERT_ERROR);
        }
        return JsonResult.success();
    }

    @ApiOperation(value = "根据跟进Id获取回复")
    @GetMapping("/{followId:[\\w]+}")
    public JsonResult selectByFollowId(@ApiIgnore @RequestAttribute("staff") Staff staff,@PathVariable("followId") String followId){
        return JsonResult.success(followCommentService.selectByFollowId(staff.getCompanyId(),followId));
    }
}

