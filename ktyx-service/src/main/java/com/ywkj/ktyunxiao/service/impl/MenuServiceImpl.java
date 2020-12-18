package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.MenuMapper;
import com.ywkj.ktyunxiao.model.Menu;
import com.ywkj.ktyunxiao.model.vo.MenuVO;
import com.ywkj.ktyunxiao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/28 13:42
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectByRoleId(String companyId, int roleId) {
        return menuMapper.selectByRoleId(companyId,roleId);
    }

    public List<MenuVO> selectAllMenuByRoleId(String companyId, int roleId){
        return menuMapper.selectAllMenuByRoleId(companyId,roleId);
    }

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }


}
