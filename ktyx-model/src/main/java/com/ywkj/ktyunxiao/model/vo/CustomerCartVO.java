package com.ywkj.ktyunxiao.model.vo;

import com.ywkj.ktyunxiao.model.Cart;
import com.ywkj.ktyunxiao.model.Customer;
import lombok.Data;

import java.util.List;

/**客户 购物车 视图对象
 * @author MiaoGuoZhu
 * @date 2018/6/5 15:17
 */
@Data
public class CustomerCartVO extends Customer {


    /**
     * 一对多
     * 一个客户对应多个购物车商品
     */
    private List<GoodsAndImageVO> goodsAndImageVOS;
}
