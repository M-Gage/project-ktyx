package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.RoleMapper;
import com.ywkj.ktyunxiao.model.Role;
import com.ywkj.ktyunxiao.service.RoleMenuService;
import com.ywkj.ktyunxiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/5/28 0028 17:45
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<Role> selectListByCompanyId(String companyId) {
        return roleMapper.selectListByCompanyId(companyId);
    }

    @Override
    public boolean selectRoleNameByCompanyId(String companyId, String roleName) {
        return roleMapper.selectRoleNameByCompanyId(companyId,roleName) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(Role role, String[] menuIdArray) {
        int i = roleMapper.insert(role);
        if (i == 1 && menuIdArray != null && menuIdArray.length != 0) {
            roleMenuService.insertList(role.getCompanyId(), role.getRoleId(), menuIdArray);
        }
    }
}
