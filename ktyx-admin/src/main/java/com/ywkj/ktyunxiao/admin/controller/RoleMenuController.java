package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/06/21 17:06
 */
@RestController()
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @PostMapping("/{roleId:[\\d]+}")
    public JsonResult insertList(@SessionAttribute("staff")Staff staff, @PathVariable("roleId")int roleId,@RequestBody() String[] menuIdArray){
        roleMenuService.insertList(staff.getCompanyId(), roleId, menuIdArray);
        return JsonResult.success();
    }
}
