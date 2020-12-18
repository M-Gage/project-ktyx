package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Dept;
import com.ywkj.ktyunxiao.model.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/4 17:20
 */
public interface DeptMapper {

    /**
     * 根据id模糊查询获取部门集合
     * @param staff
     * @return
     */
    List<Dept> selectListByCompanyId(Staff staff);

    /**
     * 根据公司ID和上级ID查询部门名称
     * @param companyId
     * @param deptId
     * @param deptName
     * @return
     */
    int selectDeptNameByCompanyId(@Param("companyId") String companyId, @Param("deptId") String deptId, @Param("deptName") String deptName);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    int insert(Dept dept);

    /**
     * 获取下一级部门数量
     * @param parentDeptId
     * @param companyId
     * @return
     */
    int selectDeptCount(@Param("parentDeptId") String parentDeptId, @Param("companyId") String companyId);

    /**获取下一级部门ID
     * @param deptId
     * @return
     */
    List<String> selectDeptIdByPreDept(String deptId);
}


