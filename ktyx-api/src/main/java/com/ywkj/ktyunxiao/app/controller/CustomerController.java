package com.ywkj.ktyunxiao.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.CustomerPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerSearchPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerVO;
import com.ywkj.ktyunxiao.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.UnsupportedEncodingException;

/**
 * @author LiWenHao
 * @date 2018/6/12 13:43
 */
@Api(description = "客户")
@RestController("apiCustomer")
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value="客户列表")
    @GetMapping("/list")
    public JsonResult selectCustomerList (@ApiIgnore @RequestAttribute("staff") Staff staff, @RequestParam("param") String param) {
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
        customerSearchPojo.setDeptId(staff.getDeptId());
        return JsonResult.success(customerService.selectLimit(customerSearchPojo));
    }

    @ApiOperation(value="添加客户")
    @PostMapping("")
    public JsonResult addCustomer(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                  @Validated @RequestBody CustomerVO customerVO){
        customerVO.setCustomerId(StringUtil.getUUID());
        customerVO.setCompanyId(staff.getCompanyId());
        customerVO.setHelpCode(PinYinUtil.getPinYinHeadChar(customerVO.getCustomerName()));
        customerVO.setStaffId(staff.getStaffId());
        customerVO.setStaffName(staff.getStaffName());
        customerVO.setDeptId(staff.getDeptId());
        customerService.insert(customerVO);
        return JsonResult.success();
    }

    @ApiOperation(value="修改客户")
    @PutMapping("")
    public JsonResult updateCustomer(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                     @RequestBody CustomerPojo customerPojo) {
        customerPojo.setCompanyId(staff.getCompanyId());
        customerService.updateByPrimaryId(customerPojo);
        return JsonResult.success();
    }

    @ApiOperation(value="查找客户名称")
    @GetMapping("/findName")
    public JsonResult selectCustomerName (@ApiIgnore @RequestAttribute("staff") Staff staff,
                                          @RequestParam("customerName") String customerName,
                                          @RequestParam(value = "customerId",required = false) String customerId) {
        return JsonResult.success(customerService.selectCustomerName(StringUtil.replaceSpace(customerName),customerId,staff.getCompanyId()));
    }

    @ApiOperation(value="查找客户经纬度")
    @GetMapping("/findCoordinate")
    public JsonResult findLonAndLat(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                    @RequestParam("lon")double lon,
                                    @RequestParam("lat")double lat,
                                    @RequestParam(value = "customerId",required = false)String customerId) {
        return JsonResult.success(customerService.selectLonAndLat(lon,lat,staff.getCompanyId(),customerId));
    }

    @ApiOperation(value="获取单个客户")
    @GetMapping("/{customerId:[\\w]+}")
    public JsonResult selectCustomer(@ApiIgnore @RequestAttribute("staff") Staff staff,@PathVariable("customerId") String customerId){
        return JsonResult.success(customerService.selectByPrimaryId(customerId,staff.getCompanyId()));
    }

    @ApiOperation(value = "经纬度区间获取客户(包含错误客户)")
    @GetMapping("/map")
    public JsonResult selectLonAndLatBetween(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                             @ApiParam(name = "右上角经纬度,逗号隔开") @RequestParam String rightTopPoint,
                                             @ApiParam(name = "左下角经纬度,逗号隔开") @RequestParam String leftBottomPoint){
        if (rightTopPoint.contains(",") && leftBottomPoint.contains(",")) {
            String [] longitudeArray = {rightTopPoint.split(",")[0], leftBottomPoint.split(",")[0]};
            String [] LatitudeArray = {rightTopPoint.split(",")[1], leftBottomPoint.split(",")[1]};
            return JsonResult.success(customerService.selectLonAndLatBetween(LatitudeArray,longitudeArray,staff));
        } else {
            throw new ParamException();
        }
    }
}
