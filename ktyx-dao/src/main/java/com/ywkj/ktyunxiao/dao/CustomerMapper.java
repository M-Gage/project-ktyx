package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Customer;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.CustomerSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerStatementSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelCustomerPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 15:30
 */
@Component
public interface CustomerMapper {

    /**
     * 添加客户
     * @param customerVO
     * @return
     */
    int insert(CustomerVO customerVO);

    /**
     * 根据主键更新
     * @param customer
     * @return
     */
    int updateByPrimaryId(Customer customer);

    /**
     * 根据公司Id查询客户名称
     * @param customerName
     * @param companyId
     * @return
     */
    int selectCustomerNameByCompanyId(@Param("customerName") String customerName, @Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 根据公司Id查询客户编号
     * @param customerNo
     * @param companyId
     * @return
     */
    int selectCustomerNoByCompanyId(@Param("customerNo") String customerNo, @Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 客户查询分页
     * @param customerSearchPojo
     * @return
     */
    List<Customer> selectLimit(@Param("pojo") CustomerSearchPojo customerSearchPojo);

    /**
     * 客户查询分页数量
     * @param customerSearchPojo
     * @return
     */
    int selectLimitCount(@Param("pojo") CustomerSearchPojo customerSearchPojo);

    /**
     * 根据客户Id修改状态
     * @param customerId
     * @param status
     * @return
     */
    int updateStatusByPrimaryId(@Param("customerId") String customerId, @Param("status") int status);

    /**
     * 查找经纬度根据公司Id
     * @param lonArray
     * @param latArray
     * @param companyId
     * @return
     */
    int selectLonAndLat(@Param("lonArray") double[] lonArray, @Param("latArray") double[] latArray, @Param("companyId") String companyId, @Param("customerId") String customerId);

    /**
     * 获取下级客户
     * @param staff
     * @param keyWord
     * @return
     */
    List<Customer> selectSubordinateCustomer(@Param("pojo") Staff staff, @Param("keyWord")String keyWord);

    /**
     * 根据客户Id修改归属人
     * @param staffId
     * @param customerIdArray
     * @return
     */
    int updateListStaffId(@Param("staffId") String staffId, @Param("staffName") String staffName, @Param("customerIdArray") String[] customerIdArray, @Param("companyId") String companyId);

    /**
     * 根据客户id获取客户
     * @param customerId
     * @param companyId
     * @return
     */
    Customer selectByPrimaryId(@Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 根据经纬度区间查找客户
     * @param longitudeArray
     * @param latitudeArray
     * @param staff
     * @return
     */
    List<Customer> selectLonAndLatBetween(@Param("longitudeArray") String[] longitudeArray,@Param("latitudeArray") String[] latitudeArray,  @Param("pojo") Staff staff);

    /**查询客户下单频率和金额趋势
     * @param c
     * @return
     */
    List<StatementVO> selectCustomerOrderFrequencyAndAmount(CustomerStatementSearchPojo c);

    /**查询客户下单商品排行
     * @param c
     * @return
     */
    List<StatementVO> selectCustomerOrderType(CustomerStatementSearchPojo c);

    /**查询客户下单类型排行
     * @param c
     * @return
     */
    List<StatementVO> selectCustomerOrderGoods(CustomerStatementSearchPojo c);

    /**查询客户下单平均商品数量排行
     * @param c
     * @return
     */
    List<StatementVO> selectCustomerOrderGoodsAvg(CustomerStatementSearchPojo c);

    /**查询客户标签对应销售额
     * @param c
     * @return
     */
    List<StatementVO> selectCustomerLabelInfo(CustomerStatementSearchPojo c);

    /**查询客户各年龄段平均消费额
     * @param c
     * @return
     */
    List<StatementVO> selectCustomerAgeOrderAvg(CustomerStatementSearchPojo c);

    /**
     * 导出客户
     * @param staff
     * @return
     */
    List<ExcelCustomerPojo> exportCustomer(@Param("pojo") Staff staff);

    /**
     * 批量插入客户
     * @param customerList
     * @return
     */
    int insertList(@Param("customerList") List<Customer> customerList);

    /**修改最后下单时间
     * @param customerId
     * @return
     */
    int updateLastOrderTime(@Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 修改最后跟进时间
     * @param customerId
     * @return
     */
    int updateLastFollowTime(@Param("customerId") String customerId, @Param("companyId") String companyId);
}
