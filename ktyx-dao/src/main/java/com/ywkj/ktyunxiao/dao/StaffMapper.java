package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelStaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffSearchPojo;
import com.ywkj.ktyunxiao.model.pojo.StaffStatementPojo;
import com.ywkj.ktyunxiao.model.vo.GoodsAmountVO;
import com.ywkj.ktyunxiao.model.vo.MonthContrastVO;
import com.ywkj.ktyunxiao.model.vo.StatementVO;
import com.ywkj.ktyunxiao.model.vo.StaffTableLookUpVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/5/21 0021 17:24
 */
@Component
public interface StaffMapper {

    /**
     * 根据账号密码查找用户
     *
     * @param phone 手机号
     * @param pwd   密码
     * @return
     */
    List<StaffPojo> selectByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);

    /**
     * 用户分页
     *
     * @param staffSearchPojo
     * @return
     */
    List<Staff> selectLimit(@Param("staff") StaffSearchPojo staffSearchPojo);

    /**
     * 用户分页数量
     *
     * @param staffSearchPojo
     * @return
     */
    int selectLimitCount(@Param("staff") StaffSearchPojo staffSearchPojo);

    /**
     * 查询手机号码
     *
     * @param phone
     * @return
     */
    int selectPhone(@Param("phone") String phone, @Param("staffId") String staffId);

    /**
     * 查询编号
     *
     * @param staff
     * @param staffNo
     * @param staffNo
     * @return
     */
    int selectStaffNoByCompanyId(@Param("staff") Staff staff, @Param("staffNo") String staffNo, @Param("staffId") String staffId);

    /**
     * 添加职员
     *
     * @param staff
     * @return
     */
    int insert(Staff staff);

    /**
     * 根据职员Id获取职员详细信息
     *
     * @param staff
     * @param staffId
     * @return
     */
    Staff selectByPrimaryId(@Param("staff") Staff staff, @Param("staffId") String staffId);

    /**
     * 修改职员
     *
     * @param staff
     * @return
     */
    int updateByPrimaryId(Staff staff);

    /**
     * 根据职员id修改状态
     *
     * @param staffId
     * @param state
     * @return
     */
    int updateStateByPrimaryId(@Param("staffId") String staffId, @Param("state") int state);

    /**
     * 获取管理员下级职员
     *
     * @param staff
     * @param keyWord
     * @return
     */
    List<Staff> selectSubordinateStaff(@Param("staff") Staff staff,
                                       @Param("keyWord") String keyWord);

    /**
     * 根据部门Id获取职员
     *
     * @param companyId
     * @param deptId
     * @param firstNumber
     * @param lastNumber
     * @return
     */
    List<Staff> selectByDeptId(@Param("companyId") String companyId, @Param("deptId") String deptId, @Param("firstNumber") int firstNumber, @Param("lastNumber") int lastNumber);

    /**
     * 根据部门Id获取职员数量
     *
     * @param companyId
     * @param deptId
     * @return
     */
    int selectByDeptIdCount(@Param("companyId") String companyId, @Param("deptId") String deptId);

    /**
     * 修改职员部门Id
     *
     * @param companyId
     * @param deptId
     * @param staffIdArray
     * @return
     */
    int updateDeptId(@Param("companyId") String companyId, @Param("deptId") String deptId, @Param("deptName") String deptName, @Param("staffIdArray") String[] staffIdArray);

    /**
     * 修改职员密码
     *
     * @param companyId
     * @param staffId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    int updatePwdByPrimaryId(@Param("companyId") String companyId, @Param("staffId") String staffId, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

    /**
     * 同比新增客户
     *
     * @param staffId
     * @param companyId
     * @return
     */
    MonthContrastVO selectNewCustomerAddNumber(@Param("staffId") String staffId,
                                               @Param("companyId") String companyId);

    /**
     * 同比成交客户
     *
     * @param staffId
     * @param companyId
     * @return
     */
    MonthContrastVO selectDealCustomerNumber(@Param("staffId") String staffId,
                                             @Param("companyId") String companyId);

    /**
     * 同比成交金额
     *
     * @param staffId
     * @param companyId
     * @return
     */
    MonthContrastVO selectDealAmount(@Param("staffId") String staffId,
                                     @Param("companyId") String companyId);

    /**
     * 同比客户更进
     *
     * @param staffId
     * @param companyId
     * @return
     */
    MonthContrastVO selectCustomerFollowupRecord(@Param("staffId") String staffId,
                                                 @Param("companyId") String companyId);

    /**
     * 员工商品成交排行
     *
     * @param staffId
     * @param companyId
     * @return
     */
    List<GoodsAmountVO> selectDealGoodsRank(@Param("staffId") String staffId, @Param("companyId") String companyId);


    StaffTableLookUpVO selectNewCustomerSchedule(@Param("staffId") String staffId, @Param("companyId") String companyId);

    StaffTableLookUpVO selectDealCustomerSchedule(@Param("staffId") String staffId, @Param("companyId") String companyId);

    StaffTableLookUpVO selectDealActiveSchedule(@Param("staffId") String staffId, @Param("companyId") String companyId);

    StaffTableLookUpVO selectFollowActiveSchedule(@Param("staffId") String staffId, @Param("companyId") String companyId);

    StaffTableLookUpVO selectSchedule(@Param("staffId") String staffId, @Param("companyId") String companyId);

    StaffTableLookUpVO selectFollow(@Param("staffId") String staffId, @Param("companyId") String companyId);

    /**
     * 员工各个阶段营销金额
     *
     * @param staffId
     * @param companyId
     * @return
     */
    StaffTableLookUpVO selectMoneySchedule(@Param("staffId") String staffId, @Param("companyId") String companyId);

    /**
     * 职员入职信息统计
     *
     * @param staffStatementPojo
     * @return
     */
    List<StatementVO> selectStaffJoin(StaffStatementPojo staffStatementPojo);

    /**
     * 职员首次操作信息统计
     *
     * @param startDate
     * @param endDate
     * @param deptId
     * @return
     */
    List<StatementVO> selectStaffOperate(@Param("startDate") String startDate,
                                         @Param("endDate") String endDate,
                                         @Param("deptId") String deptId);

    /**管理员下级部门信息统计
     * @param deptId
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    List<StatementVO> selectStaffDeptInfo(@Param("deptId") String deptId,
                                          @Param("companyId") String companyId,
                                          @Param("startDate") String startDate,
                                          @Param("endDate") String endDate);

    /**员工性别平均销售信息统计
     * @param deptId
     * @param companyId
     * @param startDate
     * @param endDate
     * @return
     */
    List<StatementVO> selectStaffSexInfo(@Param("deptId") String deptId, @Param("companyId") String companyId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**查询各年龄段员工销售信息
     * @param s
     * @return
     */
    List<StatementVO> selectStaffAgeAnalyse(StaffStatementPojo s);

    /**
     * 导出职员
     * @param staff
     * @return
     */
    List<ExcelStaffPojo> exportStaff(@Param("staff") Staff staff);

    /**
     * 批量插入
     * @param staffList
     * @return
     */
    int insertList(@Param("staffList") List<Staff> staffList);

    /**
     * 批量重置密码
     * @param staffIdArray
     * @param pwd
     * @param companyId
     * @return
     */
    int updatePwdByPrimaryArray(@Param("staffIdArray") String[] staffIdArray, @Param("pwd") String pwd, @Param("companyId") String companyId);

    /**员工销售趋势
     * @param s
     * @return
     */
    List<StatementVO> selectStaffAmount(StaffStatementPojo s);
    /**员工新增客户趋势
     * @param s
     * @return
     */
    List<StatementVO> selectStaffAddNewCustomer( StaffStatementPojo s);

    /**员工新增跟进趋势
     * @param s
     * @return
     */
    List<StatementVO> selectStaffNewFollow(StaffStatementPojo s);

    /**
     * 查询经验值
     * @param staffId
     * @param companyId
     * @return
     */
    int selectExperienceByStaffId(@Param("staffId") String staffId, @Param("companyId") String companyId);

    /**
     * 修改职员经验值
     * @param staffId
     * @param companyId
     * @param experience
     * @return
     */
    int updateExperienceByStaffId(@Param("staffId") String staffId, @Param("companyId") String companyId, @Param("experience") int experience);



}
