package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Role;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/5/28 17:42
 */
public interface RoleService {

    List<Role> selectListByCompanyId(String companyId);

    boolean selectRoleNameByCompanyId(String companyId, String roleName);

    void insert(Role role,String[] menuIdArray);
}
