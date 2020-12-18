package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Dept;
import com.ywkj.ktyunxiao.model.Staff;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/5 8:58
 */
public interface DeptService {

    List<Dept> selectListByCompanyId(Staff staff);

    boolean selectDeptNameByCompanyId(String companyId, String deptId, String deptName);

    boolean insert(Dept dept);
}
