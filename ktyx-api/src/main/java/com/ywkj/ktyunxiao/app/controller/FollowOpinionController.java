package com.ywkj.ktyunxiao.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.FollowOpinion;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.FollowOpinionPojo;
import com.ywkj.ktyunxiao.service.FollowOpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/07/09 15:56
 */
@Api(description = "跟进意见")
@RestController("apiFollowOpinion")
@RequestMapping("/api/followOpinion")
public class FollowOpinionController {

    @Autowired
    private FollowOpinionService followOpinionService;

    @ApiOperation("添加跟进意见")
    @PostMapping("")
    public JsonResult insert(@ApiIgnore @RequestAttribute("staff") Staff staff, @RequestBody FollowOpinionPojo followOpinionPojo){
        followOpinionPojo.setCompanyId(staff.getCompanyId());
        followOpinionPojo.setStaffId(staff.getStaffId());
        followOpinionPojo.setStaffName(staff.getStaffName());
        followOpinionService.insert(followOpinionPojo,followOpinionPojo.getOpinion());
        return JsonResult.success();
    }

    @ApiOperation("根据跟进Id获取意见")
    @GetMapping("/{followId:[\\w]+}")
    public JsonResult selectByFollowId(@ApiIgnore @RequestAttribute("staff") Staff staff,@PathVariable("followId") String followId){
        return JsonResult.success(followOpinionService.selectByFollowId(staff.getCompanyId(),followId));
    }

    @ApiOperation("删除跟进意见")
    @DeleteMapping("/{followId:[\\w]+}")
    public JsonResult deleteByFollowId(@ApiIgnore @RequestAttribute("staff") Staff staff,
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
