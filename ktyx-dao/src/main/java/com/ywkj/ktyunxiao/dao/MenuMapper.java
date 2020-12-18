package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Menu;
import com.ywkj.ktyunxiao.model.vo.MenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/28 13:42
 */
@Component
public interface MenuMapper {

    /**
     * 根据角色Id获取菜单列表
     * @param companyId
     * @param roleId
     * @return
     */
    List<Menu> selectByRoleId(@Param("companyId") String companyId,
                              @Param("roleId") int roleId);

    /**
     * 根据角色Id获取全部菜单列表
     * @param companyId
     * @param roleId
     * @return
     */
    List<MenuVO> selectAllMenuByRoleId(@Param("companyId") String companyId, @Param("roleId") int roleId);

    /**
     * 获取所有的菜单
     * @return
     */
    List<Menu> selectAll();
}
