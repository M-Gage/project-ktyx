package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Label;
import com.ywkj.ktyunxiao.model.Staff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 15:04
 */
@Component
public interface LabelMapper {

    /**
     * 获取标签集合
     * @param staff
     * @return
     */
    List<Label> selectList(Staff staff);

    /**
     * 添加私有标签
     * @param label
     * @return
     */
    int insert(Label label);

    /**
     * 根据公司Id和职员Id查找标签名称
     * @param companyId
     * @param labelName
     * @return
     */
    int selectLabelName(@Param("companyId") String companyId,@Param("labelName") String labelName, @Param("staffId") String staffId);

    /**
     * 查询不是私有标签
     * @param companyId
     * @return
     */
    List<Label> selectNotPrivate(String companyId);

    /**
     * 根据公司Id和标签名称
     * @param companyId
     * @param labelName
     * @return
     */
    List<Label> selectLabelNameByCompanyId(@Param("companyId") String companyId, @Param("labelName") String labelName);

    /**
     * 修改私有标签为共有标签
     * @param labelId
     * @param staffId
     * @param staffName
     * @return
     */
    int updatePrivate(@Param("labelId") int labelId, @Param("staffId") String staffId, @Param("staffName") String staffName, @Param("companyId") String companyId);

    /**
     * 根据Id批量删除
     * @param labelIdList
     * @param companyId
     * @return
     */
    int deleteListByPrimaryId(@Param("labelIdList") List<Integer> labelIdList,@Param("companyId") String companyId);

    /**
     * 根据公司Id分页
     * @param companyId
     * @param firstNumber
     * @param lastNumber
     * @return
     */
    List<Label> selectLimit(@Param("companyId") String companyId, @Param("firstNumber") int firstNumber, @Param("lastNumber") int lastNumber);

    /**
     * 分页数量
     * @param companyId
     * @return
     */
    int selectLimitCount(String companyId);

    /**
     * 修改标签
     * @param label
     * @return
     */
    int updateByPrimaryId(Label label);
}
