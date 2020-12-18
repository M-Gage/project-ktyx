package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ExperienceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/07/20 15:19
 */
@Api(description = "经验值流水")
@RestController("apiExperience")
@RequestMapping("/api/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @ApiOperation(value = "经验值流水列表(分页)")
    @GetMapping("/list")
    public JsonResult selectLimit(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                  @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("pageSize") int pageSize){
        int firstNumber = (pageNumber - 1) * pageSize;
        Map<String, Object> map = experienceService.selectLimit(staff.getCompanyId(), staff.getStaffId(), firstNumber, pageSize);
        return JsonResult.success(map);
    }
}
