package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LiWenHao
 * @date 2018/5/30 15:42
 */
@RestController("apiMenu")
@RequestMapping("/api/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;

    @GetMapping("/role")
    public JsonResult getMenuByRoleId(@ApiIgnore @RequestAttribute("staff")Staff staff){
        return JsonResult.success(menuService.selectByRoleId(staff.getCompanyId(),staff.getRoleId()));
    }
}
