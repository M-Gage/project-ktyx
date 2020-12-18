package com.ywkj.ktyunxiao.model.vo;

import com.ywkj.ktyunxiao.model.Menu;
import com.ywkj.ktyunxiao.model.Role;
import lombok.Data;

import java.util.List;

/**
 * 角色视图对象
 *
 * @author MiaoGuoZhu
 * @date 2018/5/23 14:47
 */
@Data
public class RoleVO extends Role {

    /**
     * 一个角色包含多个资源
     */
    private List<Menu> menuList;
}
