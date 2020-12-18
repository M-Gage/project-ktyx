package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.utils.DateUtil;
import com.ywkj.ktyunxiao.common.utils.ListUtil;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.StaffMapper;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelStaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffStatementPojo;
import com.ywkj.ktyunxiao.model.vo.GoodsAmountVO;
import com.ywkj.ktyunxiao.model.vo.MonthContrastVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.model.vo.StaffTableLookUpVO;
import com.ywkj.ktyunxiao.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * @author LiWenHao
 * @date 2018/5/21 17:34
 */
@Service
public class StaffServiceImpl implements StaffService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private StaffMapper staffMapper;


    @Override
    public StaffPojo selectByPhoneAndPwd(String phone, String pwd) {
        List<StaffPojo> staffPojoList = staffMapper.selectByPhoneAndPwd(phone, pwd);
        return staffPojoList.size() != 0 ? staffPojoList.get(0) : null;
    }

    @Override
    public Map<String, Object> selectLimit(StaffSearchPojo staffSearchPojo) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("rows", staffMapper.selectLimit(staffSearchPojo));
        map.put("total", staffMapper.selectLimitCount(staffSearchPojo));
        return map;
    }

    @Override
    public boolean selectPhone(String phone, String staffId) {
        return staffMapper.selectPhone(phone, staffId) == SUCCESS_INT;
    }

    @Override
    public boolean selectStaffNoByCompanyId(Staff staff, String phone, String staffId) {
        return staffMapper.selectStaffNoByCompanyId(staff, phone, staffId) == SUCCESS_INT;
    }

    @Override
    public boolean insert(Staff staff) {
        return staffMapper.insert(staff) == SUCCESS_INT;
    }

    @Override
    public Staff selectByPrimaryId(Staff staff, String staffId) {
        return staffMapper.selectByPrimaryId(staff, staffId);
    }

    @Override
    public boolean updateByPrimaryId(Staff staff) {
        return staffMapper.updateByPrimaryId(staff) == SUCCESS_INT;
    }

    @Override
    public boolean updateStateByPrimaryId(String staffId, int state) {
        return staffMapper.updateStateByPrimaryId(staffId, state) == SUCCESS_INT;
    }

    @Override
    public List<Staff> selectSubordinateStaff(Staff staff, String keyWord) {
        return staffMapper.selectSubordinateStaff(staff, keyWord);
    }

    @Override
    public Map<String, Object> selectByDeptId(String companyId, String deptId, int firstNumber, int lastNumber) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("rows", staffMapper.selectByDeptId(companyId, deptId, firstNumber, lastNumber));
        map.put("total", staffMapper.selectByDeptIdCount(companyId, deptId));
        return map;
    }

    @Override
    public boolean updateDeptId(String companyId, String deptId, String deptName, String[] staffIdArray) {
        return staffMapper.updateDeptId(companyId, deptId, deptName, staffIdArray) == staffIdArray.length;
    }

    @Override
    public boolean updatePwdByPrimaryId(String companyId, String staffId, String oldPwd, String newPwd) {
        return staffMapper.updatePwdByPrimaryId(companyId, staffId, oldPwd, newPwd) == SUCCESS_INT;
    }

    @Override
    public List<MonthContrastVO> selectStatisticsByStaffId(String staffId, Staff staff) {
        List<MonthContrastVO> monthContrastList = new ArrayList<>();
        monthContrastList.add(staffMapper.selectNewCustomerAddNumber(staffId, staff.getCompanyId()));
        monthContrastList.add(staffMapper.selectDealCustomerNumber(staffId, staff.getCompanyId()));
        monthContrastList.add(staffMapper.selectDealAmount(staffId, staff.getCompanyId()));
        monthContrastList.add(staffMapper.selectCustomerFollowupRecord(staffId, staff.getCompanyId()));
        return monthContrastList;
    }

    @Override
    public List<GoodsAmountVO> selectDealGoodsRank(String staffId, String companyId) {
        return staffMapper.selectDealGoodsRank(staffId, companyId);
    }

    @Override
    public List<StaffTableLookUpVO> selectSchedule1(String staffId, Staff staff) {
        List<StaffTableLookUpVO> tableLookUpVOS = new ArrayList<>();
        tableLookUpVOS.add(staffMapper.selectNewCustomerSchedule(staffId, staff.getCompanyId()));
        tableLookUpVOS.add(staffMapper.selectDealCustomerSchedule(staffId, staff.getCompanyId()));
        tableLookUpVOS.add(staffMapper.selectDealActiveSchedule(staffId, staff.getCompanyId()));
        tableLookUpVOS.add(staffMapper.selectFollowActiveSchedule(staffId, staff.getCompanyId()));
        tableLookUpVOS.add(staffMapper.selectSchedule(staffId, staff.getCompanyId()));
        tableLookUpVOS.add(staffMapper.selectFollow(staffId, staff.getCompanyId()));
        return tableLookUpVOS;
    }

    @Override
    public StaffTableLookUpVO selectMoneySchedule(String staffId, String companyId) {
        return staffMapper.selectMoneySchedule(staffId, companyId);
    }

    @Override
    public List<StatementVO> selectStaffJoin(StaffStatementPojo s, Staff staff) {
        addStaffIdAndCompanyId(staff, s);
        List<StatementVO> statementVOS = staffMapper.selectStaffJoin(s);
        List<String> stringList = s.isMonth() ? DateUtil.collectLocalDateMonths(LocalDate.parse(s.getStartDate()), LocalDate.parse(s.getEndDate())) :
                DateUtil.collectLocalDateYears(LocalDate.parse(s.getStartDate()), LocalDate.parse(s.getEndDate()));
        List<StatementVO> orderStatement = new ArrayList<>();
        setStatementIsDay(stringList, orderStatement, statementVOS, s.isMonth());
        return orderStatement;
    }

    @Override
    public List<StatementVO> selectStaffOperate(String bigInterval,  Staff staff) {
        return staffMapper.selectStaffOperate(bigInterval.substring(0, 10),
                bigInterval.substring(bigInterval.length() - 10),
                staff.getDeptId());
    }

    @Override
    public List<StatementVO> selectStaffDeptInfo(Staff staff, String dateInterval) {
        return staffMapper.selectStaffDeptInfo(staff.getDeptId(),
                staff.getCompanyId(),
                dateInterval.substring(0, 10),
                dateInterval.substring(dateInterval.length() - 10));
    }

    @Override
    public List<StatementVO> selectStaffSexInfo(Staff staff, String dateInterval) {
        return staffMapper.selectStaffSexInfo(staff.getDeptId(),
                staff.getCompanyId(),
                dateInterval.substring(0, 10),
                dateInterval.substring(dateInterval.length() - 10));
    }

    @Override
    public List<StatementVO> selectStaffAgeAnalyse(Staff staff , StaffStatementPojo s) {
        addStaffIdAndCompanyId(staff, s);
        return staffMapper.selectStaffAgeAnalyse(s);
    }



    @Override
    public List<StatementVO> selectStaffAmount(Staff staff, StaffStatementPojo s) {
        addStaffIdAndCompanyId(staff, s);
        return staffMapper.selectStaffAmount(s);
    }

    @Override
    public List<StatementVO> selectStaffAddNewCustomer(Staff staff, StaffStatementPojo s) {
        addStaffIdAndCompanyId(staff,s);
        return staffMapper.selectStaffAddNewCustomer(s);
    }

    @Override
    public List<StatementVO> selectStaffNewFollow(Staff staff, StaffStatementPojo s) {
        addStaffIdAndCompanyId(staff,s);
        return staffMapper.selectStaffNewFollow(s);
    }

    @Override
    public int selectExperienceByStaffId(String staffId, String companyId) {
        return staffMapper.selectExperienceByStaffId(staffId,companyId);
    }

    @Override
    public boolean updateExperienceByStaffId(String staffId, String companyId, int experience) {
        return staffMapper.updateExperienceByStaffId(staffId,companyId,experience) == SUCCESS_INT;
    }

    @Override
    public List<ExcelStaffPojo> exportStaff(Staff staff) {
        return staffMapper.exportStaff(staff);
    }

    @Override
    public int insertList(Staff staff, List<List<String>> excelData) {
        //成功条数
        int successCount = 0;
        List<Staff> staffList = new ArrayList<>();
        //跳过标题
        for (int i = 1; i < excelData.size(); i++) {
            List<String> objectList = excelData.get(i);
            //数据完整性
            if (objectList.size() != SystemConfig.staffExcelColumnCount) {
                throw new CheckException("第" + i + "行数据不完整");
            }
            Staff excelStaff = new Staff (
                    StringUtil.getUUID(),
                    objectList.get(2),
                    objectList.get(3),
                    objectList.get(0),
                    objectList.get(6).equals("正常") ? 0 : 1,
                    staff.getDeptId(),
                    staff.getDeptName(),
                    staff.getCompanyId(),
                    objectList.get(1),
                    PinYinUtil.getPinYinHeadChar(objectList.get(1)),
                    objectList.get(4),
                    DateUtil.format(objectList.get(5)),
                    SystemConfig.uploadRoleId,
                    SystemConfig.uploadRoleName
            );
            staffList.add(excelStaff);
            if (staffList.size() == SystemConfig.uploadMaxQuantity) {
                successCount += staffMapper.insertList(staffList);
                staffList.clear();
            }
        }
        if (ListUtil.isNotEmpty(staffList)) {
            successCount += staffMapper.insertList(staffList);
        }
        return successCount;
    }

    @Override
    public void updatePwdByPrimaryArray(String[] staffIdArray, String pwd, String companyId) {
        staffMapper.updatePwdByPrimaryArray(staffIdArray,pwd,companyId);
    }
    private void addStaffIdAndCompanyId(Staff staff, StaffStatementPojo s) {
        if (s.getStaffId() == null) {
            s.setStaffId(staff.getStaffId());
        }
        s.setCompanyId(staff.getCompanyId());
    }

    private void setStatementIsDay(List<String> stringList, List<StatementVO> statement, List<StatementVO> statementPojos, boolean isMonth) {
        for (String s : stringList) {
            StatementVO statementPojo = new StatementVO();
            statementPojo.setSum(0);

            statementPojo.setExistsDate(isMonth ? s.substring(0, 7) : s.substring(0, 4));
            for (StatementVO pojo : statementPojos) {
                if (isMonth ? s.contains(pojo.getExistsDate().substring(0, 7)) : s.contains(pojo.getExistsDate().substring(0, 4))) {
                    statementPojo = pojo;
                    break;
                }
            }
            statement.add(statementPojo);
        }
    }
}
