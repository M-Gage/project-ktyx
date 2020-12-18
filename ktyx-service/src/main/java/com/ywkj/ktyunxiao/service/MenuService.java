package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Menu;
import com.ywkj.ktyunxiao.model.vo.MenuVO;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/28 13:38
 */
public interface MenuService {

    List<Menu> selectByRoleId(String companyId, int roleId);

    List<MenuVO> selectAllMenuByRoleId(String companyId, int roleId);

    List<Menu> selectAll();
}
