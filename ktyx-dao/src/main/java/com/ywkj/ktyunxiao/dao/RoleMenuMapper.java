package com.ywkj.ktyunxiao.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author LiWenHao
 * @date 2018/06/21 17:07
 */
@Component
public interface RoleMenuMapper {
    /**
     * 批量插入角色菜单
     * @param companyId
     * @param roleId
     * @param menuIdArray
     * @return
     */
    int insertList(@Param("companyId") String companyId, @Param("roleId") int roleId, @Param("menuIdArray") String[] menuIdArray);

    /**
     * 根据角色Id删除角色菜单
     * @param companyId
     * @param roleId
     */
    void deleteByRoleId(@Param("companyId") String companyId, @Param("roleId") int roleId);
}
