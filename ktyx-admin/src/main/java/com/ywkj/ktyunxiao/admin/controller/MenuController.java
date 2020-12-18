package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/3/23 21:23
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("")
    public JsonResult selectAll(){
        return JsonResult.success(menuService.selectAll());
    }

    @GetMapping("/{roleId:[\\d]+}")
    public JsonResult selectByRoleId(@SessionAttribute("staff") Staff staff, @PathVariable("roleId") int roleId){
        return JsonResult.success(menuService.selectAllMenuByRoleId(staff.getCompanyId(),roleId));
    }

}
