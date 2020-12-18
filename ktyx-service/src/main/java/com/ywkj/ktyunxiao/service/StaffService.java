package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelStaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffStatementPojo;
import com.ywkj.ktyunxiao.model.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/5/21 17:34
 */
public interface StaffService {

    StaffPojo selectByPhoneAndPwd(String phone, String pwd);

    Map<String,Object> selectLimit(StaffSearchPojo staffSearchPojo);

    boolean selectPhone(String phone,String staffId);

    boolean selectStaffNoByCompanyId(Staff staff, String phone,String staffId);

    boolean insert(Staff staff);

    Staff selectByPrimaryId(Staff staff,String staffId);

    boolean updateByPrimaryId(Staff staff);

    boolean updateStateByPrimaryId(String staffId, int state);

    List<Staff> selectSubordinateStaff(Staff staff, String keyWord);

    Map<String, Object> selectByDeptId(String companyId, String deptId, int firstNumber, int lastNumber);

    boolean updateDeptId(String companyId, String deptId,String deptName, String[] staffIdArray);

    boolean updatePwdByPrimaryId(String companyId, String staffId, String oldPwd, String newPwd);

    List<ExcelStaffPojo> exportStaff(Staff staff);

    int insertList(Staff staff, List<List<String>> excelData);

    void updatePwdByPrimaryArray(String[] staffIdArray, String pwd, String companyId);


    /**查询职员上月与上上月对比
     * @param staffId
     * @param staff
     * @return
     */
    List<MonthContrastVO> selectStatisticsByStaffId(String staffId, Staff staff);

    /**员工商品销售排行
     * @param staffId
     * @param companyId
     * @return
     */
    List<GoodsAmountVO> selectDealGoodsRank(String staffId, String companyId);

    /**客户一览表
     * @param staffId
     * @param staff
     * @return
     */
    List<StaffTableLookUpVO> selectSchedule1(String staffId, Staff staff);

    /**职员一览表
     * @param staffId
     * @param companyId
     * @return
     */
    StaffTableLookUpVO selectMoneySchedule(String staffId, String companyId);

    /**
     * 职员入职信息统计
     *
     * @param staffStatementPojo
     * @return
     */
    List<StatementVO> selectStaffJoin(StaffStatementPojo staffStatementPojo, Staff staff);

    /**职员首次操作信息统计
     * @param bigInterval
     * @param staff
     * @return
     */
    List<StatementVO> selectStaffOperate(String bigInterval, Staff staff);

    /**管理员下级部门信息统计
     * @param staff
     * @param dateInterval
     * @return
     */
    List<StatementVO> selectStaffDeptInfo(Staff staff, String dateInterval);

    /**员工性别平均销售信息统计
     * @param staff
     * @param dateInterval
     * @return
     */
    List<StatementVO> selectStaffSexInfo(Staff staff, String dateInterval);

    /**员工年龄段销售信息统计
     * @param staff
     * @return
     */
    List<StatementVO> selectStaffAgeAnalyse(Staff staff , StaffStatementPojo s);


    /**员工销售趋势
     * @param staff
     * @param s
     * @return
     */
    List<StatementVO> selectStaffAmount(Staff staff, StaffStatementPojo s);

    /**员工新增客户趋势
     * @param staff
     * @param s
     * @return
     */
    List<StatementVO> selectStaffAddNewCustomer(Staff staff, StaffStatementPojo s);

    /**员工新增跟进趋势
     * @param staff
     * @param s
     * @return
     */
    List<StatementVO> selectStaffNewFollow(Staff staff, StaffStatementPojo s);

    int selectExperienceByStaffId(String staffId, String companyId);

    boolean updateExperienceByStaffId(String staffId, String companyId, int experience);

}
