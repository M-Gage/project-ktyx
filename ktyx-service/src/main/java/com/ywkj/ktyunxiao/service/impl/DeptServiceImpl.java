package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.DeptMapper;
import com.ywkj.ktyunxiao.model.Dept;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/5 8:59
 */
@Service
public class DeptServiceImpl implements DeptService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private DeptMapper deptMapper;


    @Override
    public List<Dept> selectListByCompanyId(Staff staff) {
        return deptMapper.selectListByCompanyId(staff);
    }

    @Override
    public boolean selectDeptNameByCompanyId(String companyId, String deptId, String deptName) {
        return deptMapper.selectDeptNameByCompanyId(companyId,deptId,deptName) == SUCCESS_INT;
    }

    @Override
    public boolean insert(Dept dept) {
        int index = deptMapper.selectDeptCount(dept.getDeptId(), dept.getCompanyId());
        dept.setDeptId(dept.getDeptId() + StringUtil.getIdCard(index + 1, 2));
        return deptMapper.insert(dept) == SUCCESS_INT;
    }
}
