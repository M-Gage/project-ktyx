package com.ywkj.ktyunxiao.service.impl;


import com.alibaba.fastjson.JSON;
import com.ywkj.ktyunxiao.common.annotation.Exp;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.ExpType;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.utils.DateUtil;
import com.ywkj.ktyunxiao.common.utils.ListUtil;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.CustomerMapper;
import com.ywkj.ktyunxiao.model.*;
import com.ywkj.ktyunxiao.model.pojo.CustomerPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.CustomerStatementSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelCustomerPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LiWenHao
 * @date 2017/12/20 18:12
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final int SUCCESS_INT = SystemConfig.successRow;

    private final double OFFSET = SystemConfig.lonAndLatOffset;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CustomerLabelService customerLabelService;

    @Autowired
    private ErrorCustomerService errorCustomerService;

    @Autowired
    private MapChartCityService mapChartCityService;


    @Exp(value = ExpType.INSERT_CUSTOMER)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(CustomerVO customerVO) {
        List<Contact> contactList = customerVO.getContactList();
        List<CustomerLabel> customerLabelList = customerVO.getCustomerLabelList();
        String companyId = customerVO.getCompanyId();
        String customerId = customerVO.getCustomerId();
        if(customerMapper.selectCustomerNameByCompanyId(customerVO.getCustomerName(), null, customerVO.getCompanyId()) == SUCCESS_INT){
            throw new CheckException("客户名称重复！");
        }
        if(customerMapper.selectCustomerNoByCompanyId(customerVO.getCustomerName(), null, customerVO.getCompanyId()) == SUCCESS_INT){
            throw new CheckException("客户编号重复！");
        }
        //经纬度区间
        double[] lonArray = {customerVO.getLongitude() - OFFSET, customerVO.getLongitude() + OFFSET};
        double[] latArray = {customerVO.getLatitude() - OFFSET, customerVO.getLatitude() + OFFSET};
        if(customerMapper.selectLonAndLat(lonArray,latArray,customerVO.getCompanyId(),null) == SUCCESS_INT){
            throw new CheckException("地理位置重复！");
        }
        if (!(customerMapper.insert(customerVO) == SUCCESS_INT)) {
            throw new RuntimeException("客户添加失败！");
        }
        if (ListUtil.isNotEmpty(contactList)) {
            //主要联系人数量
            int mainCount = (int) contactList.stream().filter(c -> c.getIsMain() == 1).count();
            //没有设置主要联系人默认第一个
            if (mainCount == 0) {
                contactList.get(0).setIsMain(1);
            } else if (mainCount > 1) {
                //多个主要联系人设置为第一个
                contactList.forEach(c -> {c.setIsMain(0);});
                contactList.get(0).setIsMain(1);
            }
            if (!contactService.insertList(setParam(contactList,companyId,customerId))) {
                throw new RuntimeException("联系人添加失败！");
            }
        }
        if (ListUtil.isNotEmpty(customerLabelList)) {
            if (!customerLabelService.insertList(customerLabelList, customerId, companyId)) {
                throw new RuntimeException("客户标签添加失败！");
            }
        }
        //地图报表
        MapChartCity mapChartCity = new MapChartCity(customerVO.getProvince().split("省")[0],customerVO.getCity(),customerVO.getCompanyId(),1,new BigDecimal(0));
        mapChartCityService.insertOrUpdate(mapChartCity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByPrimaryId(CustomerPojo customerPojo) {
        List<Contact> contactList = customerPojo.getContactList();
        List<CustomerLabel> customerLabelList = customerPojo.getCustomerLabelList();
        String companyId = customerPojo.getCompanyId();
        String customerId = customerPojo.getCustomerId();
        if(customerMapper.selectCustomerNameByCompanyId(customerPojo.getCustomerName(), customerPojo.getCustomerId(), customerPojo.getCompanyId()) == SUCCESS_INT){
            throw new CheckException("客户名称重复！");
        }
        if(customerMapper.selectCustomerNoByCompanyId(customerPojo.getCustomerName(), customerPojo.getCustomerId(), customerPojo.getCompanyId()) == SUCCESS_INT){
            throw new CheckException("客户编号重复！");
        }
        //经纬度区间
        double[] lonArray = {customerPojo.getLongitude() - OFFSET, customerPojo.getLongitude() + OFFSET};
        double[] latArray = {customerPojo.getLatitude() - OFFSET, customerPojo.getLatitude() + OFFSET};
        if(customerMapper.selectLonAndLat(lonArray,latArray,customerPojo.getCompanyId(),customerPojo.getCustomerId()) == SUCCESS_INT){
            throw new CheckException("地理位置重复！");
        }
        if (!(customerMapper.updateByPrimaryId(customerPojo) == SUCCESS_INT)) {
            throw new CheckException("客户更新失败！");
        }
        if (ListUtil.isNotEmpty(contactList)) {
            List<Contact> updateContactList = contactList.stream().filter(item -> StringUtil.isNotEmpty(item.getContactId())).collect(Collectors.toList());
            List<Contact> addContactList = contactList.stream().filter(item -> !StringUtil.isNotEmpty(item.getContactId())).collect(Collectors.toList());
            String[] deleteContactIdArray = customerPojo.getDeleteContactIdArray();
            if (deleteContactIdArray != null && deleteContactIdArray.length > 0) {
                if (!contactService.deleteListByPrimaryId(deleteContactIdArray, customerId, companyId)) {
                    throw new RuntimeException("联系人删除失败！");
                }
            }
            if (ListUtil.isNotEmpty(addContactList)) {
                if (!contactService.insertList(setParam(addContactList, companyId, customerId))) {
                    throw new RuntimeException("联系人添加失败！");
                }
            }
            if (ListUtil.isNotEmpty(updateContactList)) {
                if (!contactService.updateList(updateContactList)) {
                    throw new RuntimeException("联系人更新失败！");
                }
            }
        }
        if (ListUtil.isNotEmpty(customerLabelList)) {
            //标签名称为不空数量
            long nameCount = customerLabelList.stream().filter(cl -> StringUtil.isNotEmpty(cl.getLabelName())).count();
            if (nameCount != customerLabelList.size()) {
                throw new CheckException("客户标签名称缺失！");
            }
            if (!customerLabelService.updateListByCustomerId(customerLabelList,customerId,companyId)) {
                throw new RuntimeException("标签更新失败！");
            }
        }
    }

    @Override
    public boolean selectCustomerName(String customerName,String customerId, String companyId) {
        return customerMapper.selectCustomerNameByCompanyId(customerName,customerId,companyId) == SUCCESS_INT;
    }

    @Override
    public boolean selectCustomerNo(String customerNo, String customerId, String companyId) {
        return customerMapper.selectCustomerNoByCompanyId(customerNo,customerId,companyId) == SUCCESS_INT;
    }

    @Override
    public Map<String, Object> selectLimit(CustomerSearchPojo customerSearchPojo) {
        Map<String,Object> map = new HashMap<>();
        map.put("rows", customerMapper.selectLimit(customerSearchPojo));
        map.put("total", customerMapper.selectLimitCount(customerSearchPojo));
        return map;
    }

    @Override
    public boolean updateStatusByPrimaryId(String customerId, int status) {
        return customerMapper.updateStatusByPrimaryId(customerId,status) == SUCCESS_INT;
    }

    @Override
    public List<Customer> selectSubordinateCustomer(Staff staff, String keyWord) {
        return customerMapper.selectSubordinateCustomer(staff, keyWord);
    }

    @Override
    public boolean selectLonAndLat(double lon, double lat,String companyId,String customerId) {
        //经纬度区间
        double[] lonArray = {lon - OFFSET, lon + OFFSET};
        double[] latArray = {lat - OFFSET, lat + OFFSET};
        return customerMapper.selectLonAndLat(lonArray,latArray,companyId,customerId) > 0;
    }

    //TODO 修改客户归属人牵扯东西过多 目前只做到修改customer表
    @Override
    public void updateListStaffId(String staffId, String staffName, String[] customerIdArray,String companyId) {
        customerMapper.updateListStaffId(staffId, staffName, customerIdArray, companyId);
    }

    @Override
    public Customer selectByPrimaryId(String customerId, String companyId) {
        return customerMapper.selectByPrimaryId(customerId,companyId);
    }

    @Override
    public List<ExcelCustomerPojo> exportCustomer(Staff staff) {
        return customerMapper.exportCustomer(staff);
    }

    @Override
    public Map<String, Object> selectLonAndLatBetween(String[] longitudeArray, String[] latitudeArray, Staff staff) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("customer", customerMapper.selectLonAndLatBetween(longitudeArray,latitudeArray,staff));
        map.put("errorCustomer", errorCustomerService.selectLonAndLatBetween(longitudeArray,latitudeArray,staff));
        return map;
    }

    @Override
    public List<StatementVO> selectCustomerOrderFrequencyAndAmount(CustomerStatementSearchPojo c, Staff staff) {
        c.setCompanyId(staff.getCompanyId());
        c.setStaffId(staff.getStaffId());
        List<StatementVO> statement = new ArrayList<>();

        List<StatementVO> statementPojos = customerMapper.selectCustomerOrderFrequencyAndAmount(c);

        List<String> stringList = c.isDay() ? DateUtil.collectLocalDateDays(LocalDate.parse(c.getStartDate()), LocalDate.parse(c.getEndDate()))
                : DateUtil.collectLocalDateMonths(LocalDate.parse(c.getStartDate()), LocalDate.parse(c.getEndDate()));
        setStatementIsDay(stringList, statement, statementPojos, c.isDay());
        return statement;
    }

    @Override
    public Map<String,List<StatementVO>> selectCustomerOrderGoodsAndType(CustomerStatementSearchPojo c, Staff staff) {
        c.setCompanyId(staff.getCompanyId());
        c.setStaffId(staff.getStaffId());
        Map<String,List<StatementVO>> map =new HashMap<>(16);
        List<StatementVO> statementGoods = customerMapper.selectCustomerOrderGoods(c);
        List<StatementVO> statementType = customerMapper.selectCustomerOrderType(c);
        List<StatementVO> statementGoodsAvg = customerMapper.selectCustomerOrderGoodsAvg(c);
        map.put("goods",statementGoods);
        map.put("type",statementType);
        map.put("goodsAvg",statementGoodsAvg);
        return map;
    }

    @Override
    public List<StatementVO> selectCustomerLabelInfo(CustomerStatementSearchPojo c, Staff staff) {
        c.setCompanyId(staff.getCompanyId());
        c.setStaffId(staff.getStaffId());
        return customerMapper.selectCustomerLabelInfo(c);
    }

    @Override
    public List<StatementVO> selectCustomerAgeOrderAvg(CustomerStatementSearchPojo c, Staff staff) {
        c.setCompanyId(staff.getCompanyId());
        c.setStaffId(staff.getStaffId());
        return customerMapper.selectCustomerAgeOrderAvg(c);
    }

    @Override
    public int insertList(Staff staff, List<List<String>> excelData) {
        int successRow = 0;
        List<Customer> customerList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();
        if (excelData != null) {
            for (int i = 1; i < excelData.size(); i++) {
                List<String> objectList = excelData.get(i);
                //数据完整性
                if (objectList.size() != SystemConfig.customerExcelColumnCount) {
                    throw new CheckException("第" + i + "行数据不完整");
                }
                String customerId = StringUtil.getUUID();
                Customer customer = new Customer(
                        customerId,
                        objectList.get(1),
                        objectList.get(11).equals("正常") ? 0 : 1,
                        objectList.get(0),
                        staff.getStaffId(),
                        DateUtil.format(objectList.get(5)),
                        staff.getStaffName(),
                        SystemConfig.uploadCustomerLon,
                        SystemConfig.uploadCustomerLat,
                        objectList.get(7),
                        objectList.get(6),
                        objectList.get(8),
                        objectList.get(9),
                        objectList.get(4).toUpperCase() + "级",
                        PinYinUtil.getPinYinHeadChar(objectList.get(1)),
                        objectList.get(10).equals("无")  ? null : objectList.get(10),
                        staff.getCompanyId(),
                        staff.getDeptId()
                );
                if ((!objectList.get(2).equals("无")) && (!objectList.get(3).equals("无"))) {
                    Contact contact = new Contact(
                            StringUtil.getUUID(),
                            customerId,
                            objectList.get(2),
                            objectList.get(3),
                            1,
                            staff.getCompanyId()
                    );
                    contactList.add(contact);
                }
                customerList.add(customer);
                if (customerList.size() == SystemConfig.uploadMaxQuantity) {
                    successRow += customerMapper.insertList(customerList);
                    contactService.insertList(contactList);
                    customerList.clear();
                    contactList.clear();
                }
            }
            if (ListUtil.isNotEmpty(customerList)) {
                successRow += customerMapper.insertList(customerList);
                contactService.insertList(contactList);
            }
        } else {
            throw new CheckException("数据为空！");
        }
        return successRow;
    }

    @Override
    public boolean updateLastFollowTime(String customerId, String companyId) {
        return customerMapper.updateLastFollowTime(customerId,companyId) == SUCCESS_INT;
    }

    private List<Contact> setParam(List<Contact> contactList,String companyId,String customerId) {
        contactList.forEach(c -> {
            c.setCompanyId(companyId);
            c.setContactId(StringUtil.getUUID());
            c.setCustomerId(customerId);
        });
        return contactList;
    }

    private void setStatementIsDay(List<String> stringList, List<StatementVO> statement, List<StatementVO> statementPojo, boolean isDay) {
        for (String date : stringList) {
            StatementVO s = new StatementVO();
            s.setSum(0);
            s.setAmount(0.0);
            s.setExistsDate(date);
            for (StatementVO pojo : statementPojo) {
                if (isDay ? date.equals(pojo.getExistsDate()) : date.contains(pojo.getExistsDate().substring(0, 7))) {
                    s = pojo;
                    break;
                }
            }
            statement.add(s);
        }
    }
}
