package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/5/28 0028 17:46
 */
@Component
public interface RoleMapper {

    /**
     * 获取角色列表
     * @param companyId
     * @return
     */
    List<Role> selectListByCompanyId(String companyId);

    /**
     * 查找角色名称
     * @param companyId
     * @param roleName
     * @return
     */
    int selectRoleNameByCompanyId(@Param("companyId") String companyId, @Param("roleName") String roleName);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int insert(Role role);

}
