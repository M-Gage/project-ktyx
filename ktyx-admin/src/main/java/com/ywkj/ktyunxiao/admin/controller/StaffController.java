package com.ywkj.ktyunxiao.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.ExcelUtil;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Menu;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffStatementPojo;
import com.ywkj.ktyunxiao.model.vo.MonthContrastVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.model.vo.StaffTableLookUpVO;
import com.ywkj.ktyunxiao.service.MenuService;
import com.ywkj.ktyunxiao.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/5/21 17:39
 */
@Slf4j
@RequestMapping(value = "/staff")
@RestController
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/login")
    public JsonResult login(@RequestBody Staff staff, HttpSession session) {
        if (StringUtil.isNotEmpty(staff.getPhone()) && StringUtil.isNotEmpty(staff.getPassword())) {
            StaffPojo staffPojo = staffService.selectByPhoneAndPwd(staff.getPhone(), staff.getPassword());
            if (staffPojo != null && staffPojo.getStaffName() != null) {
                if (staffPojo.getIsDeptManager() != 1) {
                    return JsonResult.error(Code.NOT_DEPT_MANAGER);
                }
                if (staffPojo.getStaffStatus() == SystemConfig.disabledStatus) {
                    return JsonResult.error(Code.ACCOUNT_DISABLED);
                }
                List<Menu> menus = menuService.selectByRoleId(staffPojo.getCompanyId(), staffPojo.getRoleId());
                session.setAttribute("menu", menus);
                session.setAttribute("staff", staffPojo);
                return JsonResult.success(staffPojo);
            }
            return JsonResult.error(Code.ACCOUNT_ERROR);
        }
        return JsonResult.error(Code.LOGIN_ERROR);
    }


    @GetMapping("/logout")
    public void logout (HttpSession httpSession){
        if (httpSession.getAttribute("staff") != null) {
            httpSession.removeAttribute("staff");
        }
    }

    @PostMapping("")
    public JsonResult addStaff(@SessionAttribute("staff") Staff sessionStaff, @RequestBody Staff staff) {
        staff.setStaffId(StringUtil.getUUID());
        staff.setCompanyId(sessionStaff.getCompanyId());
        staff.setHelpCode(PinYinUtil.getPinYinHeadChar(staff.getStaffName()));
        if (staffService.insert(staff)) {
            return JsonResult.success();
        } else {
            return JsonResult.error(Code.INSERT_ERROR);
        }
    }

    @GetMapping("/list")
    public JsonResult getList(@SessionAttribute("staff") Staff staff, @RequestParam("param") String param) {
        StaffSearchPojo staffSearchPojo = null;
        try {
            staffSearchPojo = JSON.parseObject(StringUtil.urlDecoder(param), StaffSearchPojo.class);
        } catch (UnsupportedEncodingException | JSONException e) {
            throw new ParamException();
        }
        staffSearchPojo.setCompanyId(staff.getCompanyId());
        staffSearchPojo.setStaffId(staff.getStaffId());
        staffSearchPojo.setDeptId(staff.getDeptId());
        staffSearchPojo.setIsDeptManager(staff.getIsDeptManager());
        staffSearchPojo.setPageNumber((staffSearchPojo.getPageNumber() - 1) * staffSearchPojo.getPageSize());
        return JsonResult.success(staffService.selectLimit(staffSearchPojo));
    }

    @GetMapping("/findPhone")
    public JsonResult findPhone(@RequestParam(value = "phone") String phone,
                                @RequestParam(value = "staffId", required = false) String staffId) {
        return JsonResult.success(staffService.selectPhone(StringUtil.replaceSpace(phone), staffId));
    }

    @GetMapping("/findNo")
    public JsonResult findNo(@SessionAttribute("staff") Staff staff,
                             @RequestParam(value = "staffId", required = false) String staffId,
                             @RequestParam("staffNo") String staffNo) {
        return JsonResult.success(staffService.selectStaffNoByCompanyId(staff, StringUtil.replaceSpace(staffNo), staffId));
    }

    @GetMapping("/{staffId:[\\w]+}")
    public JsonResult getStaff(@SessionAttribute("staff") Staff staff,
                               @PathVariable("staffId") String staffId) {
        return JsonResult.success(staffService.selectByPrimaryId(staff, staffId));
    }

    @PutMapping("")
    public JsonResult updateStaff(@SessionAttribute("staff") Staff sessionStaff,
                                  @RequestBody Staff staff) {
        staff.setHelpCode(PinYinUtil.getPinYinHeadChar(staff.getStaffName()));
        staff.setCompanyId(sessionStaff.getCompanyId());
        if (staffService.updateByPrimaryId(staff)) {
            return JsonResult.success();
        }
        return JsonResult.error(Code.UPDATE_ERROR);
    }

    @PutMapping("/{staffId:[\\w]+}/{state:[0|1]}")
    public JsonResult updateStaffState(@PathVariable("staffId") String staffId,
                                       @PathVariable("state") int state) {
        if (staffService.updateStateByPrimaryId(staffId, state)) {
            return JsonResult.success();
        }
        return JsonResult.error(Code.UPDATE_ERROR);
    }

    @GetMapping(value = "/subordinates")
    public JsonResult selectSubordinateStaff(@SessionAttribute("staff") Staff staff,
                                             @RequestParam(value = "keyWord", required = false) String keyWord) {
        List<Staff> staffList = staffService.selectSubordinateStaff(staff, keyWord);
        return JsonResult.success(staffList);
    }


    @GetMapping("/list/{deptId:[\\w]+}")
    public JsonResult selectCustomerByDeptId(@SessionAttribute("staff") Staff staff,
                                             @PathVariable("deptId") String deptId,
                                             @RequestParam("pageSize") int pageSize,
                                             @RequestParam("pageNumber") int pageNumber) {
        int firstNumber = (pageNumber - 1) * pageSize;
        return JsonResult.success(staffService.selectByDeptId(staff.getCompanyId(), deptId, firstNumber, pageSize));
    }

    @PutMapping("/list/{deptId:[\\w]+}/{deptName}")
    public JsonResult updateDeptId(@SessionAttribute("staff") Staff staff,
                                   @PathVariable("deptId") String deptId,
                                   @PathVariable("deptName") String deptName,
                                   @RequestBody() String[] staffIdArray) {
        return JsonResult.success(staffService.updateDeptId(staff.getCompanyId(), deptId, deptName, staffIdArray));
    }

    @PutMapping("/pwd")
    public JsonResult updatePwdByPrimary(@SessionAttribute("staff") Staff staff, @RequestBody() JSONObject jsonObject) {
        String oldPwd = jsonObject.getString("oldPwd");
        String newPwd = jsonObject.getString("newPwd");
        if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd)) {
            throw new ParamException();
        }
        if (staffService.updatePwdByPrimaryId(staff.getCompanyId(), staff.getStaffId(), oldPwd, newPwd)) {
            return JsonResult.success();
        } else {
            return JsonResult.error(Code.UPDATE_PASSWORD_ERROR);
        }
    }

    @PostMapping(value = "/uploadExcel")
    public JsonResult uploadExcel(@SessionAttribute("staff") Staff sessionStaff,
                                  @RequestParam("deptId") String deptId,
                                  @RequestParam("deptName") String deptName,
                                  @RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CheckException("文件为空！");
        }
        Staff staff = new Staff();
        BeanUtils.copyProperties(sessionStaff,staff);
        staff.setDeptId(deptId);
        staff.setDeptName(deptName);
        try {
            List<List<String>> excelData = ExcelUtil.getExcelData(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            if (excelData == null) {
                throw new CheckException("数据为空！");
            }
            int successRow = staffService.insertList(staff, excelData);
            return JsonResult.success("总条数：" + (excelData.size() - 1) + ",导入成功：" + successRow + "条");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResult.error(Code.INSERT_ERROR);
    }

    @PutMapping(value = "/resetPwd")
    public JsonResult updateListPwd(@SessionAttribute("staff") Staff staff,@RequestBody JSONObject jsonObject) {
        String[] staffIdArray = StringUtil.cutStartAndEnd(jsonObject.getString("staffIdArray")).split(",");
        String pwd = StringUtil.replaceSpace(jsonObject.getString("pwd"));
        if (StringUtil.isEmpty(pwd) || staffIdArray.length == 0) {
            throw new ParamException();
        }
        staffService.updatePwdByPrimaryArray(staffIdArray, pwd, staff.getCompanyId());
        return JsonResult.success();
    }




    /*职员信息统计*/

    /**
     * 职员信息统计月份同比
     *
     * @param staffId 职员id
     * @return
     */
    @GetMapping(value = "/statistics/{staffId}")
    public JsonResult selectStatisticsByStaffId(@PathVariable(value = "staffId") String staffId,
                                                @SessionAttribute("staff") Staff staff) {
        List<MonthContrastVO> monthContrastList = staffService.selectStatisticsByStaffId(staffId, staff);
        return JsonResult.success(monthContrastList);
    }

    /**
     * 职员商品销售信息统计
     *
     * @param staffId 职员id
     * @return
     */
    @GetMapping(value = "/goods/{staffId}")
    public JsonResult selectDealGoods(@PathVariable(value = "staffId") String staffId,
                                      @SessionAttribute("staff") Staff staff) {
        return JsonResult.success(staffService.selectDealGoodsRank(staffId, staff.getCompanyId()));
    }

    /**
     * 职员信息统计客户一览表
     *
     * @param staffId 职员id
     * @return
     */
    @GetMapping(value = "/schedule1/{staffId}")
    public JsonResult selectSchedule1(@PathVariable(value = "staffId") String staffId, @SessionAttribute("staff") Staff staff) {


        List<StaffTableLookUpVO> tableLookUpVOS = staffService.selectSchedule1(staffId, staff);
        return JsonResult.success(tableLookUpVOS);

    }

    /**
     * 职员销售信息统计
     *
     * @param staffId 职员id
     * @return
     */
    @GetMapping(value = "/schedule2/{staffId}")
    public JsonResult selectSchedule2(@PathVariable(value = "staffId") String staffId, @SessionAttribute("staff") Staff staff) {

        return JsonResult.success(staffService.selectMoneySchedule(staffId, staff.getCompanyId()));

    }

    /**
     * 职员入职信息统计
     *
     * @param staffStatementPojo
     * @return
     */
    @PostMapping(value = "/join")
    public JsonResult selectStaffJoin(@RequestBody() StaffStatementPojo staffStatementPojo,
                                      @SessionAttribute("staff") Staff staff) {
        List<StatementVO> statementVOS = staffService.selectStaffJoin(staffStatementPojo, staff);
        return JsonResult.success(statementVOS);
    }

    /**
     * 职员首次操作信息统计
     *
     * @param bigInterval
     * @param staff
     * @return
     */
    @GetMapping(value = "/operate")
    public JsonResult selectStaffJoin(String bigInterval,
                                      @SessionAttribute("staff") Staff staff) {
        List<StatementVO> statementVOS = staffService.selectStaffOperate(bigInterval, staff);
        return JsonResult.success(statementVOS);
    }

    /**
     * 管理员下级部门信息统计
     *
     * @param staff
     * @return
     */
    @GetMapping(value = "/deptInfo/{dateInterval}")
    public JsonResult selectStaffDeptInfo(@PathVariable(value = "dateInterval") String dateInterval,
                                          @SessionAttribute("staff") Staff staff) {
        List<StatementVO> statementVOS = staffService.selectStaffDeptInfo(staff,dateInterval);
        return JsonResult.success(statementVOS);
    }

    /**
     * 员工性别平均销售信息统计
     *
     * @param staff
     * @return
     */
    @GetMapping(value = "/sexInfo/{dateInterval}")
    public JsonResult selectStaffSexInfo(@PathVariable(value = "dateInterval") String dateInterval,@SessionAttribute("staff") Staff staff) {
        List<StatementVO> statementVOS = staffService.selectStaffSexInfo(staff,dateInterval);
        return JsonResult.success(statementVOS);
    }
    /**
     * 员工年龄段销售信息统计
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/ageAnalyse")
    public JsonResult selectStaffAgeAnalyse(@SessionAttribute("staff") Staff staff,@RequestBody StaffStatementPojo s) {
        List<StatementVO> statementVOS = staffService.selectStaffAgeAnalyse(staff,s);
        return JsonResult.success(statementVOS);
    }
    /**
     * 员工销售趋势
     *
     * @param staff
     * @param s
     * @return
     */
    @PostMapping(value = "/amount")
    public JsonResult selectStaffAmount(@SessionAttribute("staff") Staff staff,
                                        @RequestBody StaffStatementPojo s) {
        List<StatementVO> statementVOS = staffService.selectStaffAmount(staff,s);
        return JsonResult.success(statementVOS);
    }
    /**
     * 员工新增客户趋势
     *
     * @param staff
     * @param s
     * @return
     */
    @PostMapping(value = "/nc")
    public JsonResult selectStaffAddNewCustomer(@SessionAttribute("staff") Staff staff,
                                        @RequestBody StaffStatementPojo s) {
        List<StatementVO> statementVOS = staffService.selectStaffAddNewCustomer(staff,s);
        return JsonResult.success(statementVOS);
    }
    /**
     * 员工客户更进趋势
     *
     * @param staff
     * @param s
     * @return
     */
    @PostMapping(value = "/nf")
    public JsonResult selectStaffNewFollow(@SessionAttribute("staff") Staff staff,
                                        @RequestBody StaffStatementPojo s) {
        List<StatementVO> statementVOS = staffService.selectStaffNewFollow(staff,s);
        return JsonResult.success(statementVOS);
    }

}
