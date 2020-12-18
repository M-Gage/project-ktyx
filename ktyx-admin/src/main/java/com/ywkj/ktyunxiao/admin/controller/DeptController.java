package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Dept;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/6/4 17:17
 */
@RestController()
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/list")
    public JsonResult getDeptList (@SessionAttribute("staff") Staff staff) {
        return JsonResult.success(deptService.selectListByCompanyId(staff));
    }


    @GetMapping("/findName/{deptId:[\\w]+}/{deptName}")
    public JsonResult finaName(@SessionAttribute("staff") Staff staff,
                               @PathVariable("deptId") String deptId,
                               @PathVariable("deptName") String deptName){
        return JsonResult.success(deptService.selectDeptNameByCompanyId(staff.getCompanyId(),deptId, StringUtil.replaceSpace(deptName)));
    }

    @PostMapping("")
    public JsonResult addDept(@SessionAttribute("staff") Staff staff,
                              @RequestBody Dept dept){
        dept.setCompanyId(staff.getCompanyId());
        return JsonResult.success(deptService.insert(dept));
    }

}
