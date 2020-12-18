package com.ywkj.ktyunxiao.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Role;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/5/28 17:40
 */
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public JsonResult getRoleList(@SessionAttribute("staff") Staff staff){
        return JsonResult.success(roleService.selectListByCompanyId(staff.getCompanyId()));
    }

    @GetMapping("/findName")
    public JsonResult findName(@SessionAttribute("staff") Staff staff,
                               @RequestParam("roleName") String roleName){
        return JsonResult.success(roleService.selectRoleNameByCompanyId(staff.getCompanyId(), StringUtil.replaceSpace(roleName)));
    }

    @PostMapping("")
    public JsonResult addRole(@SessionAttribute("staff") Staff staff,
                              @RequestBody JSONObject jsonObject){
        String [] menuIdArray = null;
        if (jsonObject.get("role") != null){
            Role role = JSONObject.parseObject(JSON.toJSONString(jsonObject.get("role")), Role.class);
            role.setCompanyId(staff.getCompanyId());
            if (jsonObject.get("menuIdArray") != null) {
                menuIdArray = StringUtil.cutStartAndEnd(jsonObject.get("menuIdArray").toString()).split(",");
            }
            roleService.insert(role,menuIdArray);
        } else {
            throw new ParamException();
        }
        return JsonResult.success();
    }
}
