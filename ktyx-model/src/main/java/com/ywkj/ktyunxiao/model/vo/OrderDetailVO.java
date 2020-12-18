package com.ywkj.ktyunxiao.model.vo;

import com.ywkj.ktyunxiao.model.Order;
import com.ywkj.ktyunxiao.model.OrderDetail;
import lombok.Data;

import java.util.List;

/**订单明细视图对象
 * @author MiaoGuoZhu
 * @date 2018/6/13 15:44
 */
@Data
public class OrderDetailVO extends Order {
    /**
     * 订单详情列表
     */
    List<OrderDetail> orderDetail;
    /**
     * 带图片商品列表
     */
    List<GoodsAndImageVO> goodsAndImage;
}
