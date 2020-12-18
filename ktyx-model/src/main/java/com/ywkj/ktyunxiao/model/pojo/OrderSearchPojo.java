package com.ywkj.ktyunxiao.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import lombok.Data;

/**订单搜索对象
 * @author MiaoGuoZhu
 * @date 2018/6/12 15:21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSearchPojo extends StatementSearchBase{
    /**
     * 页数
     */
    private Integer pageNumber;
    /**
     *页面大小
     */
    private Integer pageSize;

    /**
     *客户id
     */
    private String customerId;


    /**
     *客户等级
     */
    private String level;
    /**
     *商品类型id
     */
    private String typeId;
    /**
     *部门id
     */
    private String deptId;
    /**
     *查询条件
     */
    private String condition;
    /**
     *订单状态
     */
    private String status;

}
