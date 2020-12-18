package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/07/20 17:48
 */
@Api(description = "等级")
@RestController("apiGrade")
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation(value = "获取等级列表")
    @GetMapping("/list")
    public JsonResult selectList(@ApiIgnore @RequestAttribute("staff") Staff staff){
        return JsonResult.success(gradeService.selectCompanyId(staff.getCompanyId()));
    }
}
