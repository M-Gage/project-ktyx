package com.ywkj.ktyunxiao.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.CacheName;
import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.enums.TokenAttribute;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.JwtUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.service.GoodsTypeService;
import com.ywkj.ktyunxiao.service.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/5/22 9:47
 */
@Api(description = "员工")
@RestController(value = "apiStaff")
@RequestMapping(value = "/api/staff")
public class StaffController {

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private CacheManager cacheManager;

    @Autowired
    private StaffService staffService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @ApiOperation(value="登录")
    @PostMapping("/login")
    public JsonResult getStaff(@RequestBody Staff staff){
        if(StringUtil.isNotEmpty(staff.getPhone()) && StringUtil.isNotEmpty(staff.getPassword())){
            StaffPojo staffPojo = staffService.selectByPhoneAndPwd(staff.getPhone(), staff.getPassword());
            if(staffPojo != null && staffPojo.getStaffName() != null){
                if(staffPojo.getStaffStatus() == SystemConfig.disabledStatus) {
                    return JsonResult.error(Code.ACCOUNT_DISABLED);
                }
                Map<String,Object> tokenParams = new HashMap<>(16);
                tokenParams.put(TokenAttribute.STAFF_ID.getValue(), staffPojo.getStaffId());
                tokenParams.put(TokenAttribute.COMPANY_ID.getValue(), staffPojo.getCompanyId());
                tokenParams.put(TokenAttribute.IS_DEPT_MANAGER.getValue(), staffPojo.getIsDeptManager());
                tokenParams.put(TokenAttribute.ROLE_ID.getValue(), staffPojo.getRoleId());
                tokenParams.put(TokenAttribute.STAFF_NAME.getValue(), staffPojo.getStaffName());
                tokenParams.put(TokenAttribute.DEPT_ID.getValue(), staffPojo.getDeptId());
                staffPojo.setToken(jwtUtil.createToken(tokenParams, staffPojo.getStaffName()));
                //添加到缓存
                Cache tokenCache = cacheManager.getCache(CacheName.TOKEN.getCacheName());
                tokenCache.put(staffPojo.getStaffId(),staffPojo.getToken());
                //添加商品类型缓存
                goodsTypeService.selectType4App(staffPojo.getCompanyId());
                return JsonResult.ok(Code.SUCCESS,staffPojo);
            }
            return JsonResult.error(Code.ACCOUNT_ERROR);
        }
        return JsonResult.error(Code.LOGIN_ERROR);
    }

    @ApiOperation(value="修改密码")
    @PutMapping("/pwd")
    public JsonResult updatePwdByPrimary(@ApiIgnore @RequestAttribute("staff") Staff staff, @RequestBody JSONObject jsonObject){
        String oldPwd = jsonObject.get("oldPwd").toString();
        String newPwd = jsonObject.get("newPwd").toString();
        if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd)) {
            throw new CheckException("参数不能为空");
        }
        if (staffService.updatePwdByPrimaryId(staff.getCompanyId(),staff.getStaffId(),oldPwd,newPwd)) {
            return JsonResult.success();
        } else {
            return JsonResult.error(Code.UPDATE_PASSWORD_ERROR);
        }
    }

    @ApiOperation(value="获取下级")
    @GetMapping(value = "/subordinates")
    public JsonResult selectSubordinateStaff(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                             @RequestParam(value = "keyWord", required = false) String keyWord) {
        List<Staff> staffList = staffService.selectSubordinateStaff(staff, keyWord);
        return JsonResult.success(staffList);
    }

    @ApiOperation(value="获取经验值(当前登录用户)")
    @GetMapping(value = "/exp")
    public JsonResult selectExp(@ApiIgnore @RequestAttribute("staff") Staff staff) {
        return JsonResult.success(staffService.selectExperienceByStaffId(staff.getStaffId(), staff.getCompanyId()));
    }

}

