package com.ywkj.ktyunxiao.admin.controller;


import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.OrderSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.OrderStatementSearchPojo;
import com.ywkj.ktyunxiao.model.vo.BulletinDataVO;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.vo.GoodsAmountVO;
import com.ywkj.ktyunxiao.model.vo.OrderDetailVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单信息控制器
 *
 * @author MiaoGuoZhu
 * @date 2018/1/10 11:13
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;


    private static final String MATCHS = "错误(.*)";

    /*
     * 首页
     * */

    /**
     * 分页查询7天内订单清单
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "")
    public JsonResult selectOrderList(@RequestBody OrderSearchPojo orderSearch
            , @SessionAttribute("staff") Staff staff) {
        int firstNum = (orderSearch.getPageNumber() - 1) * orderSearch.getPageSize();
        Map<String, Object> map = new HashMap<>(16);
        map.put("rows", orderService.selectOrderBy7Date(staff.getCompanyId(), firstNum, orderSearch.getPageSize(), staff.getStaffId()));
        map.put("total", orderService.selectOrderBy7DateCount(staff.getCompanyId(), staff.getStaffId()));
        return JsonResult.success(map);
    }


    /**
     * 获取首页面板的数据
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/home/bulletin")
    public JsonResult selectHomeData4bulletin(HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        BulletinDataVO bulletinDataVO = orderService.selectHomeData4Bulletin(staff);
        return JsonResult.ok(Code.SUCCESS, bulletinDataVO);
    }

    /**
     * 获取首页图表的数据
     *
     * @return
     */
    @GetMapping(value = "/home/chart")
    public JsonResult selectHomeData4Chart(@SessionAttribute("staff") Staff staff) {
        Map<String, Object> map = orderService.selectHomeData4Chart(staff.getCompanyId());
        return JsonResult.ok(Code.SUCCESS, map);
    }

    /**
     * 获取首页榜单的数据
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/home/list")
    public JsonResult selectHomeData4List(HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        Map<String, Object> map = orderService.selectHomeData4List(staff.getCompanyId());
        return JsonResult.ok(Code.SUCCESS, map);
    }

    /*
     *订单管理
     * */

    /**
     * 条件分页查询订单清单
     *
     * @param orderSearch
     * @param staff
     * @return
     */
    @PostMapping(value = "/conditions")
    public JsonResult selectOrderByManyCondition(@RequestBody OrderSearchPojo orderSearch,
                                                 @SessionAttribute("staff") Staff staff) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("rows", orderService.selectOrderByManyCondition(orderSearch, staff));
        map.put("total", orderService.selectOrderByManyConditionCount(orderSearch, staff));
        return JsonResult.success(map);
    }


    /**
     * 更改订单状态
     *
     * @param orderId
     * @param status
     * @param staff
     * @return
     */
    @GetMapping(value = "/status")
    public JsonResult updateOrderStatus(@RequestParam(value = "orderId") String orderId,
                                        @RequestParam(value = "status") Integer status,
                                        @SessionAttribute("staff") Staff staff) {
        return orderService.updateOrderStatus(staff.getCompanyId(), orderId, status) > 0 ? JsonResult.success() : JsonResult.failed();
    }

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/info/{orderId}")
    public JsonResult selectGoodsListByOrderId(@PathVariable("orderId") String orderId) {
        OrderDetailVO orderDetail = orderService.selectGoodsListByOrderId(orderId);
        return orderDetail != null ? JsonResult.success(orderDetail) : JsonResult.error(Code.IS_NULL_DATA);
    }

    /*
     * 订单报表
     * */

    /**
     * 订单金额和数量报表
     *
     * @param staff
     * @param statementSearch
     * @return
     */
    @PostMapping(value = "/orderInterval")
    public JsonResult selectOrderStatementByDateInterval(@SessionAttribute("staff") Staff staff,
                                                         @RequestBody OrderStatementSearchPojo statementSearch) {
        List<StatementVO> statement = orderService.selectOrderStatementByInterval(statementSearch, staff);
        return JsonResult.success(statement);
    }

    /**
     * 团队贡献，排行，客户贡献报表
     *
     * @param staff
     * @param statementSearch
     * @return
     */
    @PostMapping(value = "/teamInterval")
    public JsonResult selectTeamByDateInterval(@SessionAttribute("staff") Staff staff,
                                               @RequestBody OrderStatementSearchPojo statementSearch) {
        List<StatementVO> statement = orderService.selectTeamStatementByInterval(statementSearch, staff, false);
        return JsonResult.success(statement);
    }


    /**客户订货数量报表
     * @param staff
     * @param statementSearch
     * @return
     */
    @PostMapping(value = "/goodsTypeInterval")
    public JsonResult selectGoodsTypeStatementByInterval(@SessionAttribute("staff") Staff staff,
                                                   @RequestBody OrderStatementSearchPojo statementSearch) {
            List<StatementVO> statement = orderService.selectGoodsTypeStatementByInterval(statementSearch,staff,false);
            return JsonResult.success(statement);
    }

    /**
     * 日期区间商品总金额对比
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/goodsContrast")
    public JsonResult selectGoodsAmountByDateInterval(@SessionAttribute("staff") Staff staff
            , @RequestBody Map<String, Object> map) {
        List<String> goods = (ArrayList<String>) map.get("goods");
        String date1 = (String) map.get("date1");
        String date2 = (String) map.get("date2");
        date1 = date1.replace(".", "-");
        date2 = date2.replace(".", "-");
        List<List<GoodsAmountVO>> l = new ArrayList<>();
        l.add(orderService.selectGoodsAmountByDateInterval(staff.getCompanyId(), goods, date1));
        l.add(orderService.selectGoodsAmountByDateInterval(staff.getCompanyId(), goods, date2));
        return JsonResult.success( l);
    }


    /**
     * 日期区间地区销售趋势
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/area/amount")
    public JsonResult selectAreaAmount(@SessionAttribute("staff") Staff staff
            , @RequestBody OrderStatementSearchPojo oss) {
        List<StatementVO> s  = orderService.selectAreaAmount(oss,staff);
        return JsonResult.success( s);
    }

    /**
     * 日期区间地区商品类型销售趋势
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/area/type")
    public JsonResult selectAreaType(@SessionAttribute("staff") Staff staff
            , @RequestBody OrderStatementSearchPojo oss) {
        List<StatementVO> s  = orderService.selectAreaType(oss,staff);
        return JsonResult.success( s);
    }

    /**
     * 地区客户销售信息
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/area/acaa")
    public JsonResult selectAreaCustomerAvgAmount(@SessionAttribute("staff") Staff staff
            , @RequestBody OrderStatementSearchPojo oss) {
        List<StatementVO> s  = orderService.selectAreaCustomerAvgAmount(oss,staff);
        return JsonResult.success( s);
    }
    /**
     * 地区商品销售信息
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/area/aga")
    public JsonResult selectAreaGoodsAmount(@SessionAttribute("staff") Staff staff
            , @RequestBody OrderStatementSearchPojo oss) {
        List<StatementVO> s  = orderService.selectAreaGoodsAmount(oss,staff);
        return JsonResult.success( s);
    }
}
