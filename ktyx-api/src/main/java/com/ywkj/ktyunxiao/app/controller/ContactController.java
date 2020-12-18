package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Contact;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/06/27 17:33
 */
@Api(description = "联系人")
@RestController("apiContact")
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @ApiOperation(value = "添加联系人")
    @PostMapping("")
    public JsonResult insert(@ApiIgnore @RequestAttribute("staff") Staff staff, @RequestBody Contact contact){
        contact.setContactId(StringUtil.getUUID());
        contact.setCompanyId(staff.getCompanyId());
        if (contactService.insert(contact)){
            return JsonResult.success();
        }
        return JsonResult.error(Code.INSERT_ERROR);
    }

}
