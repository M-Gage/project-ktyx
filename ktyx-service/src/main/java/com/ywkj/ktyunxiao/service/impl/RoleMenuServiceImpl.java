package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.RoleMenuMapper;
import com.ywkj.ktyunxiao.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiWenHao
 * @date 2018/06/21 17:06
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void insertList(String companyId, int roleId, String[] menuIdArray) {
        if (menuIdArray.length != 0) {
            roleMenuMapper.deleteByRoleId(companyId,roleId);
        }
        roleMenuMapper.insertList(companyId,roleId,menuIdArray);
    }
}
