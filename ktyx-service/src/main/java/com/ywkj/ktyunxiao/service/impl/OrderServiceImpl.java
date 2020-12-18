package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.annotation.Exp;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.ExpType;
import com.ywkj.ktyunxiao.common.utils.DateUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.*;
import com.ywkj.ktyunxiao.model.MapChartCity;
import com.ywkj.ktyunxiao.model.Order;
import com.ywkj.ktyunxiao.model.OrderDetail;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.OrderSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.OrderStatementSearchPojo;
import com.ywkj.ktyunxiao.model.vo.*;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.service.MapChartCityService;
import com.ywkj.ktyunxiao.service.OrderService;
import com.ywkj.ktyunxiao.service.thread.OrderThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/24 9:29
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private MapChartCityService mapChartCityService;


    //    private OrderThread orderThread;
    @Override
    public BulletinDataVO selectHomeData4Bulletin(StaffPojo staff) {
        return orderMapper.selectHomeData4Bulletin(staff);
    }

    @Override
    public Map<String, Object> selectHomeData4Chart(String companyId) {
        Map<String, Object> map = new HashMap<>(16);
        List<StatementVO> statement = new ArrayList<>();
        List<StatementVO> statementPojos = orderMapper.select7DateData(companyId);
        List<String> stringList = DateUtil.collectLocalDateDays(LocalDate.parse(DateUtil.format(DateUtil.getAddDayDate(new Date(), -7))), LocalDate.parse(DateUtil.format(DateUtil.getAddDayDate(new Date(), 0))));
        setStatementIsDay(stringList, statement, statementPojos, true);
        map.put("sevenDateData", statement);
        map.put("GoodsTypeData", orderMapper.selectGoodsTypeData(companyId));
        return map;
    }

    @Override
    public Map<String, Object> selectHomeData4List(String companyId) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("GoodsData", orderMapper.selectGoodsData(companyId));
        map.put("StaffData", orderMapper.selectStaffData(companyId));
        return map;
    }

    @Exp(value = ExpType.INSERT_ORDER)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insertOrders(List<CustomerOrderVO> customerOrderVOLists, Staff staff) {
        List<Order> orderList = new ArrayList<>();
        List<OrderDetail> orderDetails = new ArrayList<>();
        //遍历订单列
        for (CustomerOrderVO customerOrderVO : customerOrderVOLists) {
            if (customerOrderVO != null) {
                List<OrderDetail> orderDetail = customerOrderVO.getOrderDetail();
                List<String> goodsIdList = new ArrayList<>();
                String customerId = customerOrderVO.getCustomerId();
                String companyId = staff.getCompanyId();
                String staffId = staff.getStaffId();
                String province = customerOrderVO.getProvince();
                String city = customerOrderVO.getCity();
                Date date = new Date();
                //是否是之后指定客户
                boolean isAppoint = customerOrderVO.getIsAppoint();

                Order order = new Order();
                order.setSubmitTime(date);
                order.setCustomerId(customerId);
                order.setCustomerName(customerOrderVO.getCustomerName());
                order.setCompanyId(companyId);
                order.setStaffId(staffId);
                order.setStaffName(staff.getStaffName());
                order.setStatus(0);
                order.setProvince(province);
                order.setCity(city);
                order.setDistrict(customerOrderVO.getDistrict());
                order.setDetailAddress(customerOrderVO.getOrderAddress());

                //订单编号
                String orderId = "10" + companyId.substring(0, 4) + date.getTime() + staffId.substring(0, 4);
                order.setOrderId(orderId);

                //总金额
                BigDecimal amount = BigDecimal.valueOf(0);
                for (OrderDetail od : orderDetail) {
                    GoodsAndImageVO goods = goodsMapper.selectGoodsByGoodsGroupOrGoodsId(od.getGoodsId(), SystemConfig.resourceName).get(0);
                    goodsIdList.add(goods.getGoodsId());
                    od.setOrderId(orderId);
                    od.setTypeId(goods.getTypeId());
                    od.setTypeName(goods.getTypeName());
                    amount = amount.add(BigDecimal.valueOf(od.getSum()).multiply(od.getGoodsPrice()));
                }
                order.setAmount(amount);
                orderList.add(order);
                //插入地图数据
                mapChartCityService.insertOrUpdate(new MapChartCity(province.split("省")[0], city, companyId, 0, amount));
                //插入订单商品
                int i = orderDetailMapper.insertList(orderDetail);
                orderDetails.addAll(orderDetail);
                if (i > 0) {
                    cartMapper.deletesCartByStaffIdAndGoodsIdArr(isAppoint ? customerId : "0", goodsIdList, staffId);
                }

                //开启缓存线程
                ExecutorService executor = Executors.newCachedThreadPool();
                //向执行器提交callable任务
                executor.submit(new OrderThread(companyId,customerId,customerMapper));
            }
        }

        //插入订单
        if (orderList.size() > 0) {
            int i = orderMapper.insertList(orderList);
            Map<String, Object> map = new HashMap<>(16);
            if (i > 0) {
                map.put("orderList", orderList);
                map.put("orderDetailList", orderDetails);
            }

            return map;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> selectOrderBy7Date(String companyId, int firstNum, Integer lastNum, String staffId) {
        return orderMapper.selectOrderBy7Date(companyId, staffId, firstNum, lastNum);
    }

    @Override
    public int selectOrderBy7DateCount(String companyId, String staffId) {
        return orderMapper.selectOrderBy7DateCount(companyId, staffId);
    }

    @Override
    public List<Order> selectOrderByManyCondition(OrderSearchPojo orderSearch, Staff staff) {
        setOrderSearch(orderSearch, staff);
        orderSearch.setPageNumber((orderSearch.getPageNumber() - 1) * orderSearch.getPageSize());
        return orderMapper.selectOrderByManyCondition(orderSearch);
    }

    @Override
    public int selectOrderByManyConditionCount(OrderSearchPojo orderSearch, Staff staff) {
        setOrderSearch(orderSearch, staff);
        return orderMapper.selectOrderByManyConditionCount(orderSearch);
    }

    @Override
    public int updateOrderStatus(String companyId, String orderId, Integer status) {
        return orderMapper.updateOrderStatus(companyId, orderId, status);
    }

    @Override
    public OrderDetailVO selectGoodsListByOrderId(String orderId) {
        return orderMapper.selectGoodsListByOrderId(orderId);
    }

    @Override
    public List<StatementVO> selectOrderStatementByInterval(OrderStatementSearchPojo statementSearch, Staff staff) {
        List<StatementVO> statement = new ArrayList<>();

        setOrderStatementSearch(statementSearch, staff);
        List<StatementVO> statementPojos = orderMapper.selectOrderStatementByInterval(statementSearch);

        List<String> stringList = statementSearch.isDay() ? DateUtil.collectLocalDateDays(LocalDate.parse(statementSearch.getStartDate()), LocalDate.parse(statementSearch.getEndDate()))
                : DateUtil.collectLocalDateMonths(LocalDate.parse(statementSearch.getStartDate()), LocalDate.parse(statementSearch.getEndDate()));
        setStatementIsDay(stringList, statement, statementPojos, statementSearch.isDay());
        return statement;
    }

    @Override
    public List<StatementVO> selectTeamStatementByInterval(OrderStatementSearchPojo s, Staff staff, boolean isApp) {
        List<StatementVO> statement = new ArrayList<>();

        setOrderStatementSearch(s, staff);

        //是否有团队贡献，后台没有，app会有
        if (s.getIsTeamContribute() != null && isApp) {

            //团队贡献
            if (s.getIsTeamContribute()) {
                //是否顶级员工（顶级员工查部门贡献）
                if (staff.getDeptId().length() == 4) {
                    for (String d : deptMapper.selectDeptIdByPreDept(staff.getDeptId())) {
                        statement.add(orderMapper.selectStaffContributeByDept(s, d, true));
                    }
                } else {
                    //不是顶级查看本部门内容
                    String preDept = staff.getDeptId().substring(0, staff.getDeptId().length() - 2);
                    statement.add(orderMapper.selectStaffContributeByDept(s, preDept, false));
                    statement.add(orderMapper.selectStaffTeamContributeByInterval(s));
                }
            } else {
                //员工排行
                if (s.getIsTeamRank()) {
                    List<StatementVO> statementPojos = orderMapper.selectTeamStatementByInterval(s);
                    StatementVO statementPojo = orderMapper.selectStaffStatementByInterval(s);
                    for (int i = 0; i < statementPojos.size(); i++) {
                        statementPojos.get(i).setRank(i);
                        if (i < 5) {
                            statement.add(statementPojos.get(i));
                        } else {
                            if (statementPojo.getAmount().equals(statementPojos.get(i).getAmount()) && statementPojo.getStaffName().equals(statementPojos.get(i).getStaffName())) {
                                statement.add(statementPojos.get(i));
                            }
                        }
                    }
                } else {
                    //客户排行
                    statement = orderMapper.selectTeamStatementByInterval(s);
                }
            }
        } else {
            //客户和员工排行
            statement = orderMapper.selectTeamStatementByInterval(s);
        }
        return statement;
//        return null;
    }

    @Override
    public List<StatementVO> selectGoodsTypeStatementByInterval(OrderStatementSearchPojo s, Staff staff, boolean isApp) {
        setOrderStatementSearch(s, staff);
        s.setApp(false);
        List<StatementVO> allStatement = orderMapper.selectGoodsTypeStatementByInterval(s);
        if (!isApp) {
            return allStatement;
        }
        s.setApp(true);
        List<StatementVO> statement = orderMapper.selectGoodsTypeStatementByInterval(s);

        IntSummaryStatistics stats1 = statement.stream().mapToInt((x) -> x.getSum()).summaryStatistics();
        IntSummaryStatistics stats2 = allStatement.stream().mapToInt((x) -> x.getSum()).summaryStatistics();

        StatementVO statementPojo = new StatementVO();
        statementPojo.setTypeName("其他");
        statementPojo.setSum((int) (stats2.getSum() - stats1.getSum()));
        statement.add(0, statementPojo);
        return statement;
    }

    @Override
    public List<GoodsAmountVO> selectGoodsAmountByDateInterval(String companyId, List<String> goods, String date) {
        return orderMapper.selectGoodsAmountByDateInterval(companyId, goods, date.substring(0, 10), date.substring(date.length() - 10, date.length()));
    }

    @Override
    public int selectStaffRank(String companyId, String staffId) {
        return orderMapper.selectStaffRank(companyId, staffId) + 1;
    }

    @Override
    public StatementVO selectRevenueByDateInterval(String dateInterval, Staff staff) {
        dateInterval = dateInterval.replace(".", "-");
        return orderMapper.selectRevenueByDateInterval(dateInterval.substring(0, 10), dateInterval.substring(dateInterval.length() - 10, dateInterval.length()), staff.getStaffId(), staff.getCompanyId());
    }

    @Override
    public List<StatementVO> selectAreaAmount(OrderStatementSearchPojo oss, Staff staff) {
        oss.setCompanyId(staff.getCompanyId());
        oss.setStaffId(staff.getStaffId());
        return orderMapper.selectAreaAmount(oss);
    }

    @Override
    public List<StatementVO> selectAreaType(OrderStatementSearchPojo oss, Staff staff) {
        oss.setCompanyId(staff.getCompanyId());
        oss.setStaffId(staff.getStaffId());
        return orderMapper.selectAreaType(oss);
    }

    @Override
    public List<StatementVO> selectAreaCustomerAvgAmount(OrderStatementSearchPojo oss, Staff staff) {
        oss.setCompanyId(staff.getCompanyId());
        oss.setStaffId(staff.getStaffId());
        return orderMapper.selectAreaCustomerAvgAmount(oss);
    }

    @Override
    public List<StatementVO> selectAreaGoodsAmount(OrderStatementSearchPojo oss, Staff staff) {
        oss.setCompanyId(staff.getCompanyId());
        oss.setStaffId(staff.getStaffId());
        return orderMapper.selectAreaGoodsAmount(oss);
    }

    private void setStatementIsDay(List<String> stringList, List<StatementVO> statement, List<StatementVO> statementPojos, boolean isDay) {
        for (String s : stringList) {
            StatementVO statementPojo = new StatementVO();
            statementPojo.setSum(0);
            statementPojo.setAmount(0.0);
            statementPojo.setExistsDate(s);
            for (StatementVO pojo : statementPojos) {
                if (isDay ? s.equals(pojo.getExistsDate()) : s.contains(pojo.getExistsDate().substring(0, 7))) {
//                    pojo.setExistsDate(pojo.getExistsDate()+"01");
                    statementPojo = pojo;
                    break;
                }
            }
            statement.add(statementPojo);
        }
    }

    private void setOrderSearch(OrderSearchPojo orderSearch, Staff staff) {
        if (!StringUtil.isNotEmpty(orderSearch.getStaffId())) {
            orderSearch.setStaffId(staff.getStaffId());
        }
        orderSearch.setCompanyId(staff.getCompanyId());
    }

    private void setOrderStatementSearch(OrderStatementSearchPojo s, Staff staff) {
        if (!StringUtil.isNotEmpty(s.getStaffId())) {
            s.setStaffId(staff.getStaffId());
        }

        s.setCompanyId(staff.getCompanyId());
    }
}


