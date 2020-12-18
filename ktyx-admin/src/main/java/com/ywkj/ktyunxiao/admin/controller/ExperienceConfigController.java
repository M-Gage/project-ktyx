package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.ExperienceConfig;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ExperienceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/07/17 9:44
 */
@RestController
@RequestMapping("/experienceConfig")
public class ExperienceConfigController {

    @Autowired
    private ExperienceConfigService experienceConfigService;

    @GetMapping("")
    public JsonResult selectByCompanyId(@SessionAttribute("staff") Staff staff){
        return JsonResult.success(experienceConfigService.selectByCompanyId(staff.getCompanyId()));
    }

    @PutMapping("")
    public JsonResult updateCompanyId(@SessionAttribute("staff") Staff staff,
                                      @RequestBody ExperienceConfig experienceConfig){
        experienceConfig.setCompanyId(staff.getCompanyId());
        experienceConfigService.updateByCompanyId(experienceConfig);
        return JsonResult.success();
    }
}
