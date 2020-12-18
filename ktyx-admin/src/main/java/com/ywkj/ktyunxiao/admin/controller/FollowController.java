package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/07/03 15:11
 */
@RestController()
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/list")
    public JsonResult selectFollowList(@SessionAttribute("staff") Staff staff, @RequestParam(value = "customerId",required = false) String customerId, @RequestParam int pageNumber, @RequestParam int pageSize){
        int firstNumber = (pageNumber -1) * pageSize;
        return JsonResult.success(followService.selectLimit(staff,customerId,firstNumber,pageSize));
    }


}
