package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.ErrorCustomer;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ErrorCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/06/27 13:32
 */
@Api(description = "错误客户")
@RestController("apiErrorCustomer")
@RequestMapping("/api/errorCustomer")
public class ErrorCustomerController {

    @Autowired
    private ErrorCustomerService errorCustomerService;

    @ApiOperation(value = "添加错误客户")
    @PostMapping("")
    public JsonResult insert(@ApiIgnore @RequestAttribute("staff") Staff staff, @Validated @RequestBody ErrorCustomer errorCustomer) {
        errorCustomer.setStaffId(staff.getStaffId());
        errorCustomer.setCompanyId(staff.getCompanyId());
        if (errorCustomerService.insert(errorCustomer)) {
            return JsonResult.success();
        }
        return JsonResult.error(Code.INSERT_ERROR);
    }
}
