package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/06/27 9:00
 */
@RestController("apiCompany")
@RequestMapping("/api/company")
@Api(description = "公司")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @ApiOperation(value="获取地图关键字与POI")
    @GetMapping("/keyword")
    public JsonResult selectMapKeyWord(@ApiIgnore @RequestAttribute("staff") Staff staff) {
        return JsonResult.success(companyService.selectMapKeyWordByPrimary(staff.getCompanyId()));
    }
}
