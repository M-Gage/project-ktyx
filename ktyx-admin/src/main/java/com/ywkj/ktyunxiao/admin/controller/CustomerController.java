package com.ywkj.ktyunxiao.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.ExcelUtil;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.CustomerPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerStatementSearchPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/6/7 15:24
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("")
    public JsonResult addCustomer(@SessionAttribute("staff") Staff staff,
                                  @Validated @RequestBody CustomerVO customerVO) {
        customerVO.setCustomerId(StringUtil.getUUID());
        customerVO.setCompanyId(staff.getCompanyId());
        customerVO.setHelpCode(PinYinUtil.getPinYinHeadChar(customerVO.getCustomerName()));
        customerVO.setStaffId(staff.getStaffId());
        customerVO.setDeptId(staff.getDeptId());
        customerVO.setStaffName(staff.getStaffName());
        customerService.insert(customerVO);
        return JsonResult.success();
    }

    @PutMapping("")
    public JsonResult updateCustomer(@SessionAttribute("staff") Staff staff,
                                     @RequestBody CustomerPojo customerPojo) {
        customerPojo.setCompanyId(staff.getCompanyId());
        customerService.updateByPrimaryId(customerPojo);
        return JsonResult.success();
    }

    @GetMapping("/findName")
    public JsonResult selectCustomerName(@SessionAttribute("staff") Staff staff,
                                         @RequestParam("customerName") String customerName,
                                         @RequestParam(value = "customerId", required = false) String customerId) {
        return JsonResult.success(customerService.selectCustomerName(StringUtil.replaceSpace(customerName), customerId, staff.getCompanyId()));
    }

    @GetMapping("/findNo")
    public JsonResult selectCustomerNo(@SessionAttribute("staff") Staff staff,
                                       @RequestParam("customerNo") String customerNo,
                                       @RequestParam(value = "customerId", required = false) String customerId) {
        return JsonResult.success(customerService.selectCustomerNo(StringUtil.replaceSpace(customerNo), customerId, staff.getCompanyId()));
    }

    @GetMapping("/list")
    public JsonResult selectList(@SessionAttribute("staff") Staff staff,
                                 @RequestParam("param") String param) {
        CustomerSearchPojo customerSearchPojo = null;
        try {
            customerSearchPojo = JSON.parseObject(StringUtil.urlDecoder(param), CustomerSearchPojo.class);
        } catch (UnsupportedEncodingException | JSONException e) {
            throw new ParamException();
        }
        //页码不能小于1
        if (customerSearchPojo.getPageNumber() < 1) {
            throw new ParamException();
        }
        customerSearchPojo.setPageNumber((customerSearchPojo.getPageNumber() - 1) * customerSearchPojo.getPageSize());
        customerSearchPojo.setCompanyId(staff.getCompanyId());
        customerSearchPojo.setStaffId(staff.getStaffId());
        customerSearchPojo.setIsDeptManager(staff.getIsDeptManager());
        if (!(StringUtil.isNotEmpty(customerSearchPojo.getDeptId()))) {
            customerSearchPojo.setDeptId(staff.getDeptId());
        }
        return JsonResult.success(customerService.selectLimit(customerSearchPojo));
    }

    @PutMapping("/{customerId:[\\w]+}/{status:[1|0]}")
    public JsonResult updateStatus(@PathVariable("customerId") String customerId,
                                   @PathVariable("status") int status) {
        return JsonResult.success(customerService.updateStatusByPrimaryId(customerId, status));
    }

    @GetMapping(value = "/subordinates")
    public JsonResult selectSubordinateStaff(@SessionAttribute("staff") Staff staff,
                                             @RequestParam(value = "staffId", required = false) String staffId,
                                             @RequestParam(value = "deptId", required = false) String deptId,
                                             @RequestParam(value = "isDeptManager", required = false) Integer isDeptManager,
                                             @RequestParam(value = "keyWord", required = false) String keyWord) {
        Staff s = JSON.parseObject(JSON.toJSONString(staff), Staff.class);
        if (StringUtil.isNotEmpty(staffId)) {
            s.setStaffId(staffId);
        }
        if (StringUtil.isNotEmpty(deptId)) {
            s.setDeptId(deptId);
        }
        if (isDeptManager != null) {
            s.setIsDeptManager(isDeptManager);
        }
        return JsonResult.success(customerService.selectSubordinateCustomer(s, keyWord));
    }

    @GetMapping("/findCoordinate")
    public JsonResult findLonAndLat(@SessionAttribute("staff") Staff staff,
                                    @RequestParam("lon") double lon,
                                    @RequestParam("lat") double lat,
                                    @RequestParam(value = "customerId", required = false) String customerId) {
        return JsonResult.success(customerService.selectLonAndLat(lon, lat, staff.getCompanyId(), customerId));
    }

    @PutMapping("/{staffId:[\\w]+}")
    public JsonResult updateListStaffId(@SessionAttribute("staff") Staff staff,
                                        @PathVariable("staffId") String staffId,
                                        @RequestBody JSONObject jsonObject) {
        String[] customerIdArray = StringUtil.cutStartAndEnd(jsonObject.getString("customerIdArray")).split(",");
        String staffName = jsonObject.getString("staffName");
        if (StringUtil.isNotEmpty(staffName) || customerIdArray.length == 0) {
            throw new ParamException();
        }
        customerService.updateListStaffId(staffId, staffName, customerIdArray, staff.getCompanyId());
        return JsonResult.success();
    }

    @GetMapping("/{customerId:[\\w]+}")
    public JsonResult selectCustomer(@SessionAttribute("staff") Staff staff, @PathVariable("customerId") String customerId) {
        return JsonResult.success(customerService.selectByPrimaryId(customerId, staff.getCompanyId()));
    }


    /**
     * 查询客户下单频率和金额趋势
     *
     * @param staff
     * @param c
     * @return
     */
    @PostMapping("/faa")
    public JsonResult selectCustomerOrderFrequencyAndAmount(@SessionAttribute("staff") Staff staff,
                                                            @RequestBody CustomerStatementSearchPojo c) {
        List<StatementVO> s = customerService.selectCustomerOrderFrequencyAndAmount(c, staff);
        return JsonResult.success(s);
    }

    /**
     * 查询客户下单商品和分类趋势
     *
     * @param staff
     * @param c
     * @return
     */
    @PostMapping("/gat")
    public JsonResult selectCustomerOrderGoodsAndType(@SessionAttribute("staff") Staff staff,
                                                      @RequestBody CustomerStatementSearchPojo c) {
        Map<String, List<StatementVO>> s = customerService.selectCustomerOrderGoodsAndType(c, staff);
        return JsonResult.success(s);
    }

    /**
     * 查询客户标签对应销售额
     *
     * @param staff
     * @param c
     * @return
     */
    @PostMapping("/labelInfo")
    public JsonResult selectCustomerLabelInfo(@SessionAttribute("staff") Staff staff,
                                              @RequestBody CustomerStatementSearchPojo c) {
        List<StatementVO> s = customerService.selectCustomerLabelInfo(c, staff);
        return JsonResult.success(s);
    }

    /**
     * 查询客户订单中各年龄段平均销售额
     *
     * @param staff
     * @param c
     * @return
     */
    @PostMapping("/aoa")
    public JsonResult selectCustomerAgeOrderAvg(@SessionAttribute("staff") Staff staff,
                                              @RequestBody CustomerStatementSearchPojo c) {
        List<StatementVO> s = customerService.selectCustomerAgeOrderAvg(c, staff);
        return JsonResult.success(s);
    }

    @PostMapping(value = "/uploadExcel")
    public JsonResult uploadExcel(@SessionAttribute("staff") Staff staff,
                                  @RequestParam("file") MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()){
            try {
                List<List<String>> excelData = ExcelUtil.getExcelData(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
                int i = customerService.insertList(staff, excelData);
                return JsonResult.ok("总条数：" + (excelData.size() -1) + ",导入成功：" + i + "条");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new CheckException("文件为空！");
        }
        return JsonResult.error(Code.INSERT_ERROR);
    }

}
