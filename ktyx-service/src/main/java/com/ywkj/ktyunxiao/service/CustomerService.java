package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Customer;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.CustomerPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerStatementSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelCustomerPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;

import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2017/12/20 18:11
 */
public interface CustomerService {

    void insert(CustomerVO customerVO);

    void updateByPrimaryId (CustomerPojo customerPojo);

    boolean selectCustomerName(String customerName,String customerId,String companyId);

    boolean selectCustomerNo(String customerNo, String customerId, String companyId);

    Map<String,Object> selectLimit(CustomerSearchPojo customerSearchPojo);

    boolean updateStatusByPrimaryId(String customerId, int status);

    List<Customer> selectSubordinateCustomer(Staff staff, String keyWord);

    boolean selectLonAndLat(double lon,double lat,String companyId,String customerId);

    void updateListStaffId(String staffId, String staffName,String[] customerIdArray, String companyId);

    Customer selectByPrimaryId(String customerId, String companyId);

    List<ExcelCustomerPojo> exportCustomer(Staff staff);

    Map<String,Object> selectLonAndLatBetween(String[] LatitudeArray, String[] longitudeArray, Staff staff);

    /**查询客户下单频率和金额趋势
     * @param c
     * @param staff
     * @return
     */
    List<StatementVO> selectCustomerOrderFrequencyAndAmount(CustomerStatementSearchPojo c, Staff staff);

    /**查询客户下单商品和分类趋势
     * @param c
     * @param staff
     * @return
     */
    Map<String,List<StatementVO>> selectCustomerOrderGoodsAndType(CustomerStatementSearchPojo c, Staff staff);

    /**查询客户标签对应销售额
     * @param c
     * @param staff
     * @return
     */
    List<StatementVO> selectCustomerLabelInfo(CustomerStatementSearchPojo c, Staff staff);

    /**查询客户各年龄段平均消费额
     * @param c
     * @param staff
     * @return
     */
    List<StatementVO> selectCustomerAgeOrderAvg(CustomerStatementSearchPojo c, Staff staff);

    int insertList(Staff staff, List<List<String>> excelData);

    boolean updateLastFollowTime(String customerId, String companyId);

}
