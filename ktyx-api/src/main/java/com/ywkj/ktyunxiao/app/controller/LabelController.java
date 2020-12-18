package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Label;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/6/7 14:33
 */
@Api(description = "标签")
@RestController("apiLabel")
@RequestMapping("/api/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @ApiOperation(value = "获取标签列表不分页")
    @GetMapping("/list")
    public JsonResult getLabelList(@ApiIgnore @RequestAttribute("staff")  Staff staff){
        return JsonResult.success(labelService.selectList(staff));
    }

    @ApiOperation(value = "添加标签(私有标签)")
    @PostMapping("")
    public JsonResult addLabel(@ApiIgnore @RequestAttribute("staff") Staff staff,@Validated @RequestBody Label label){
        label.setStaffId(staff.getStaffId());
        label.setStaffName(staff.getStaffName());
        label.setCompanyId(staff.getCompanyId());
        label.setIsPrivate(1);
        if (labelService.insert(label)) {
            return JsonResult.success();
        } else {
            return JsonResult.error(Code.INSERT_ERROR);
        }
    }

    @ApiOperation(value = "查找名称")
    @GetMapping("/findName")
    public JsonResult selectLabelName(@ApiIgnore @RequestAttribute("staff") Staff staff,@RequestParam String labelName){
        return JsonResult.success(labelService.selectLabelName(staff.getCompanyId(), StringUtil.replaceSpace(labelName),staff.getStaffId()));
    }

    @ApiOperation(value = "修改标签名称")
    @PutMapping("")
    public JsonResult update(@ApiIgnore @RequestAttribute("staff") Staff staff,@RequestBody Label label){
        label.setCompanyId(staff.getCompanyId());
        labelService.updateByPrimaryId(label);
        return JsonResult.success();
    }

}
