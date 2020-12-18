package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Grade;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/07/17 10:34
 */
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("")
    public JsonResult selectCompanyId(@SessionAttribute("staff") Staff staff){
        return JsonResult.success(gradeService.selectCompanyId(staff.getCompanyId()));
    }

    @PostMapping("")
    public JsonResult insert(@SessionAttribute("staff") Staff staff,
                             @RequestBody Grade grade){
        grade.setCompanyId(staff.getCompanyId());
        gradeService.insert(grade);
        return JsonResult.success();
    }

    @GetMapping("/findExp")
    public JsonResult selectCompanyId(@SessionAttribute("staff") Staff staff,
                                      @RequestParam int exp){
        return JsonResult.success(gradeService.selectMaxExperience(staff.getCompanyId(), exp));
    }



}
