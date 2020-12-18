package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Follow;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/07/03 15:11
 */
@Api(description = "跟进")
@RestController("apiFollow")
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @ApiOperation(value = "添加跟进")
    @PostMapping("")
    public JsonResult insertFollow(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                   @RequestParam String customerId,
                                   @RequestParam String customerName,
                                   @RequestParam(required = false) String content,
                                   @RequestPart(value = "file",required = false) MultipartFile[] multipartFileArray){
        if (StringUtil.isEmpty(content) && multipartFileArray.length == 0) {
            throw new ParamException();
        }
        Follow follow = new Follow(StringUtil.getUUID(), customerId, customerName, staff.getCompanyId(), staff.getStaffId(), staff.getStaffName(), content);
        followService.insert(follow, multipartFileArray);
        return JsonResult.success();
    }

    @ApiOperation(value = "获取跟进列表(分页)")
    @GetMapping("/list")
    public JsonResult selectFollowList(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                       @RequestParam(value = "customerId", required = false) String customerId,
                                       @RequestParam int pageNumber, @RequestParam int pageSize) {
        int firstNumber = (pageNumber - 1) * pageSize;
        return JsonResult.success(followService.selectLimit(staff, customerId, firstNumber, pageSize));
    }



}
