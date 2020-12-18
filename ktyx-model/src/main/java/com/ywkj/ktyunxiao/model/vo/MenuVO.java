package com.ywkj.ktyunxiao.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ywkj.ktyunxiao.model.Menu;
import lombok.Data;
/**菜单视图对象
 * @author LiWenHao
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuVO extends Menu {

    /**
     * 是否权限内菜单
     */
    private String flag;
}
