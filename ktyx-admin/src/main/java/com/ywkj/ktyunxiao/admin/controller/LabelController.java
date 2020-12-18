package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Label;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/6/7 14:33
 */
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/list")
    public JsonResult getLabelList(@SessionAttribute("staff") Staff staff){
        return JsonResult.success(labelService.selectList(staff));
    }

    @GetMapping("/company")
    public JsonResult selectNotPrivate(@SessionAttribute("staff") Staff staff){
        return JsonResult.success(labelService.selectNotPrivate(staff.getCompanyId()));
    }

    @GetMapping("/detailList")
    public JsonResult getLabelList(@SessionAttribute("staff") Staff staff, @RequestParam int pageNumber, @RequestParam int pageSize){
        int firstNum = (pageNumber - 1) * pageSize;
        return JsonResult.success(labelService.selectLimit(staff.getCompanyId(), firstNum, pageSize));
    }

    @PostMapping("")
    public JsonResult addLabel(@SessionAttribute("staff") Staff staff,
                               @Validated @RequestBody Label label){
        label.setStaffId(staff.getStaffId());
        label.setStaffName(staff.getStaffName());
        label.setCompanyId(staff.getCompanyId());
        labelService.insertNotPrivate(label);
        return JsonResult.success();
    }

    @GetMapping("/findName")
    public JsonResult selectLabelName(@SessionAttribute("staff") Staff staff,@RequestParam String labelName){
        return JsonResult.success(labelService.selectAllLabelName(staff.getCompanyId(),labelName));
    }

    @GetMapping("/findPrivateName")
    public JsonResult selectPrivateLabelName(@SessionAttribute("staff") Staff staff,@RequestParam String staffId,@RequestParam String labelName){
        return JsonResult.success(labelService.selectLabelName(staff.getCompanyId(),labelName,staffId));
    }

    @PutMapping("")
    public JsonResult update(@SessionAttribute("staff") Staff staff,@RequestBody Label label){
        label.setCompanyId(staff.getCompanyId());
        labelService.updateByPrimaryId(label);
        return JsonResult.success();
    }


}
