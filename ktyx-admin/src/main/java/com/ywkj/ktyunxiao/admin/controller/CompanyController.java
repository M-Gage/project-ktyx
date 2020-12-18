package com.ywkj.ktyunxiao.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Company;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/6/19 17:09
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PutMapping("/keyword")
    public JsonResult updateMapKeyWord(@SessionAttribute("staff") Staff staff,@RequestBody() JSONObject jsonObject){
        String poi = jsonObject.get("poi").toString();
        String keyWord = jsonObject.get("keyWord").toString();
        if (companyService.updateMapKeyWord(poi, keyWord, staff.getCompanyId())) {
            return JsonResult.success();
        }
        return JsonResult.error(Code.UPDATE_ERROR);
    }

    @GetMapping("/keyword")
    public JsonResult selectMapKeyWord(@SessionAttribute("staff") Staff staff) {
        return JsonResult.success(companyService.selectMapKeyWordByPrimary(staff.getCompanyId()));
    }

}
