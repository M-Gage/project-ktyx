package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Order;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.OrderSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.OrderStatementSearchPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerOrderVO;
import com.ywkj.ktyunxiao.model.vo.OrderDetailVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 10:17
 */
@Slf4j
@Api(description = "订单")
@RestController(value = "apiOrder")
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "录入订单信息")
    @PostMapping(value = "")
    public JsonResult insertOrder(@RequestBody() List<CustomerOrderVO> customerOrderVOLists,
                                  @ApiIgnore @RequestAttribute("staff") Staff staff) {
        if (customerOrderVOLists.size() > 0) {
//            for(int i = 0;i<10000;i++) {

            // todo 查询是否可售商品

            Map<String, Object> map = orderService.insertOrders(customerOrderVOLists, staff);
            List<Order> orderList = (List<Order>) map.get("orderList");

            //todo 同步到另一个库里

            return orderList != null && orderList.size() > 0 ? JsonResult.success(orderList) : JsonResult.error(Code.INSERT_ERROR);
        } else {
            return JsonResult.error(Code.PARAM_ERROR);
        }
//        return  JsonResult.success();
    }


    @ApiOperation(value = "订单后返回排行")
    @GetMapping(value = "/rank")
    public JsonResult selectStaffRank(@ApiIgnore @RequestAttribute("staff") Staff staff) {
        return JsonResult.success(orderService.selectStaffRank(staff.getCompanyId(), staff.getStaffId()));
    }


    @ApiOperation(value = "条件分页查询订单清单")
    @PostMapping(value = "/conditions")
    public JsonResult selectOrderByManyCondition(@RequestBody OrderSearchPojo orderSearch,
                                                 @ApiIgnore @RequestAttribute("staff") Staff staff) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("rows", orderService.selectOrderByManyCondition(orderSearch, staff));
        map.put("total", orderService.selectOrderByManyConditionCount(orderSearch, staff));
        return JsonResult.success(map);
    }

    @ApiOperation(value = "查询订单详情")
    @GetMapping(value = "/info/{orderId}")
    public JsonResult selectGoodsListByOrderId(@PathVariable("orderId") String orderId) {
        OrderDetailVO orderDetail = orderService.selectGoodsListByOrderId(orderId);
        return orderDetail != null ? JsonResult.success(orderDetail) : JsonResult.error(Code.IS_NULL_DATA);
    }


    @ApiOperation(value = "营收简报")
    @GetMapping(value = "/revenue")
    public JsonResult selectRevenueByDateInterval(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                                  @RequestParam(value = "dateInterval1") String dateInterval1,
                                                  @RequestParam(value = "dateInterval2") String dateInterval2) {
        List<StatementVO> statementVOS = new ArrayList<>();
        statementVOS.add(orderService.selectRevenueByDateInterval(dateInterval1, staff));
        statementVOS.add(orderService.selectRevenueByDateInterval(dateInterval2, staff));
        return JsonResult.success(statementVOS);
    }


    @ApiOperation(value = "订单金额和数量报表")
    @PostMapping(value = "/orderInterval")
    public JsonResult selectOrderStatementByDateInterval(@RequestBody OrderStatementSearchPojo statementSearch
            , @ApiIgnore @RequestAttribute("staff") Staff staff) {
        List<StatementVO> statement = orderService.selectOrderStatementByInterval(statementSearch, staff);
        return JsonResult.success(statement);
    }

    @ApiOperation(value = "团队贡献，排行，客户贡献报表")
    @PostMapping(value = "/teamInterval")
    public JsonResult selectTeamByDateInterval(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                               @RequestBody OrderStatementSearchPojo statementSearch) {
        List<StatementVO> statement = orderService.selectTeamStatementByInterval(statementSearch, staff, true);
        return JsonResult.success(statement);
    }


    @ApiOperation(value = "客户订货数量报表")
    @PostMapping(value = "/goodsTypeInterval")
    public JsonResult selectGoodsTypeStatementByInterval(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                                         @RequestBody OrderStatementSearchPojo statementSearch) {
        List<StatementVO> statement = orderService.selectGoodsTypeStatementByInterval(statementSearch, staff, true);
        return JsonResult.success(statement);
    }
}
