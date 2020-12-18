package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Order;
import com.ywkj.ktyunxiao.model.pojo.OrderSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.OrderStatementSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.vo.BulletinDataVO;
import com.ywkj.ktyunxiao.model.vo.GoodsAmountVO;
import com.ywkj.ktyunxiao.model.vo.OrderDetailVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单映射
 *
 * @author MiaoGuoZhu
 * @date 2018/5/24 9:29
 */
@Component
public interface OrderMapper {


    /**
     * 查询首页快报数据
     *
     * @param staff
     * @return
     */
    BulletinDataVO selectHomeData4Bulletin(StaffPojo staff);

    /**
     * 批量插入订单
     *
     * @param orderList
     * @return
     */
    int insertList(@Param("orderList") List<Order> orderList);

    /**
     * 分页查询7天内订单清单
     *
     * @param companyId
     * @param staffId
     * @param firstNum
     * @param pageSize
     * @return
     */
    List<Order> selectOrderBy7Date(@Param("companyId") String companyId,
                                   @Param("staffId") String staffId,
                                   @Param("firstNum") int firstNum,
                                   @Param("pageSize") Integer pageSize);

    /**
     * 查询7天内订单总数
     *
     * @param companyId
     * @param staffId
     * @return
     */
    int selectOrderBy7DateCount(@Param("companyId") String companyId,
                                @Param("staffId") String staffId);

    /**条件查询点单
     * @param orderSearch
     * @return
     */
    List<Order> selectOrderByManyCondition(OrderSearchPojo orderSearch);

    /**查询返回总数
     * @param orderSearch
     * @return
     */
    int selectOrderByManyConditionCount( OrderSearchPojo orderSearch);

    /**修改订单状态
     * @param companyId
     * @param orderId
     * @param status
     * @return
     */
    int updateOrderStatus(@Param("companyId") String companyId,
                          @Param("orderId") String orderId,
                          @Param("status") Integer status);

    /**查询订单详情
     * @param orderId
     * @return
     */
    OrderDetailVO selectGoodsListByOrderId(String orderId);

    /**根据时间区间查询订单金额和数量
     * @param oss
     * @return
     */
    List<StatementVO> selectOrderStatementByInterval(OrderStatementSearchPojo oss);

    /**根据时间区间查询团队报表
     * @param oss
     * @return
     */
    List<StatementVO> selectTeamStatementByInterval(OrderStatementSearchPojo oss);

    /**根据时间期间查询订货类型数量排行报表
     * @param oss
     * @return
     */
    List<StatementVO> selectGoodsTypeStatementByInterval(OrderStatementSearchPojo oss);

    /**根据时间期间查询某个员工报表
     * @param s
     * @return
     */
    StatementVO selectStaffStatementByInterval(OrderStatementSearchPojo s);

    /**根据时间期间查询某个部门报表
     * @param s
     * @param dept
     * @param isBoss
     * @return
     */
    StatementVO selectStaffContributeByDept(@Param("s") OrderStatementSearchPojo s,
                                            @Param("dept") String dept,
                                            @Param("isBoss") boolean isBoss);

    /**根据时间期间查询某个员工报表(若是部门管理员返回带领业绩)
     * @param s
     * @return
     */
    StatementVO selectStaffTeamContributeByInterval(OrderStatementSearchPojo s);

    /**日期区间商品总金额
     * @param companyId
     * @param goods
     * @param startDate
     * @param endDate
     * @return
     */
    List<GoodsAmountVO> selectGoodsAmountByDateInterval(@Param("companyId") String companyId,
                                                        @Param("goods") List<String> goods,
                                                        @Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);

    /**首页查询七天收入
     * @param companyId
     * @return
     */
    List<StatementVO> select7DateData(String companyId);

    /**首页查询商品分类销售饼图
     * @param companyId
     * @return
     */
    List<StatementVO> selectGoodsTypeData(String companyId);

    /**查询首页商品排行列表
     * @param companyId
     * @return
     */
    List<StatementVO> selectGoodsData(String companyId);

    /**查询首页员工排行列表
     * @param companyId
     * @return
     */
    List<StatementVO> selectStaffData(String companyId);

    /**获取员工排行
     * @param companyId
     * @param staffId
     * @return
     */
    int selectStaffRank(@Param("companyId") String companyId, @Param("staffId") String staffId);

    /**根据日期区间查询员工营收
     * @param startDate
     * @param endDate
     * @param staffId
     * @param companyId
     * @return
     */
    StatementVO selectRevenueByDateInterval(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("staffId") String staffId, @Param("companyId") String companyId);


    /**日期区间地区销售趋势
     * @param oss
     * @return
     */
    List<StatementVO> selectAreaAmount(OrderStatementSearchPojo oss);

    /**日期区间地区商品类型销售趋势
     * @param oss
     * @return
     */
    List<StatementVO> selectAreaType(OrderStatementSearchPojo oss);

    /**查询区域客户平均每笔单子金额
     * @param oss
     * @return
     */
    List<StatementVO> selectAreaCustomerAvgAmount(OrderStatementSearchPojo oss);

    /**地区商品销售信息
     * @param oss
     * @return
     */
    List<StatementVO> selectAreaGoodsAmount(OrderStatementSearchPojo oss);
}
