package com.ywkj.ktyunxiao.service;


import com.ywkj.ktyunxiao.model.Order;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.OrderSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.OrderStatementSearchPojo;
import com.ywkj.ktyunxiao.model.vo.*;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;

import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/23 18:02
 */

public interface OrderService {

    /**查询主页的六个快报
     * @param staff
     * @return
     */
    BulletinDataVO selectHomeData4Bulletin(StaffPojo staff);

    /**查询主页的图表
     * @param companyId
     * @return
     */
    Map<String, Object> selectHomeData4Chart(String companyId);

    /**查询主页的 业绩榜和商品榜
     * @param companyId
     * @return
     */
    Map<String,Object> selectHomeData4List(String companyId);




    /**批量插入订单
     * @param customerOrderVOLists
     * @param staff
     * @return
     */
    Map<String,Object> insertOrders(List<CustomerOrderVO> customerOrderVOLists, Staff staff);

    /**分页查询7天内订单清单
     * @param companyId
     * @param firstNum
     * @param pageSize
     * @param staffId
     * @return
     */
    List<Order> selectOrderBy7Date(String companyId, int firstNum, Integer pageSize, String staffId);

    /**查询7天内订单总数
     * @param companyId
     * @param staffId
     * @return
     */
    int selectOrderBy7DateCount(String companyId, String staffId);

    /**条件查询订单
     * @param orderSearch
     * @param staff
     * @return
     */
    List<Order> selectOrderByManyCondition(OrderSearchPojo orderSearch, Staff staff);

    /**返回的总数量
     * @param orderSearch
     * @param staff
     * @return
     */
    int selectOrderByManyConditionCount(OrderSearchPojo orderSearch, Staff staff);

    /**修改订单状态
     * @param companyId
     * @param orderId
     * @param status
     * @return
     */
    int updateOrderStatus(String companyId, String orderId, Integer status);

    /**根据订单编号查询明细
     * @param orderId
     * @return
     */
    OrderDetailVO selectGoodsListByOrderId(String orderId);

    /**根据日期查询订单金额和数量
     * @param statementSearch
     * @param staff
     * @return
     */
    List<StatementVO> selectOrderStatementByInterval(OrderStatementSearchPojo statementSearch, Staff staff);

    /**根据日期查询团队贡献，排行，客户贡献报表
     * @param statementSearch
     * @param staff
     * @param isApp
     * @return
     */
    List<StatementVO> selectTeamStatementByInterval(OrderStatementSearchPojo statementSearch, Staff staff, boolean isApp);

    /**根据日期查询商品类型
     * @param statementSearch
     * @param staff
     * @param isApp
     * @return
     */
    List<StatementVO> selectGoodsTypeStatementByInterval(OrderStatementSearchPojo statementSearch, Staff staff, boolean isApp);

    /**日期区间商品总金额
     * @param companyId
     * @param goods
     * @param date
     * @return
     */
    List<GoodsAmountVO> selectGoodsAmountByDateInterval(String companyId, List<String> goods, String date);


    /**查询员工排行
     * @param companyId
     * @param staffId
     * @return
     */
    int selectStaffRank(String companyId, String staffId);

    /**查询一段时间的收入
     * @param dateInterval
     * @param staff
     * @return
     */
    StatementVO selectRevenueByDateInterval(String dateInterval, Staff staff);

    /**日期区间地区销售趋势
     * @param oss
     * @param staff
     * @return
     */
    List<StatementVO> selectAreaAmount(OrderStatementSearchPojo oss, Staff staff);

    /**日期区间地区商品类型销售趋势
     * @param oss
     * @param staff
     * @return
     */
    List<StatementVO> selectAreaType(OrderStatementSearchPojo oss, Staff staff);

    /**查询区域客户平均每笔单子金额
     * @param oss
     * @param staff
     * @return
     */
    List<StatementVO> selectAreaCustomerAvgAmount(OrderStatementSearchPojo oss, Staff staff);

    /**地区商品销售信息
     * @param oss
     * @param staff
     * @return
     */
    List<StatementVO> selectAreaGoodsAmount(OrderStatementSearchPojo oss, Staff staff);
}
