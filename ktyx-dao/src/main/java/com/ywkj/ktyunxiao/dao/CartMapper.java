package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Cart;
import com.ywkj.ktyunxiao.model.vo.CustomerCartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 15:40
 */
@Component
public interface CartMapper {

    /**
     * insert cart information
     *
     * @param carts Cart list
     * @return success number
     */
    int insertList(@Param("carts") List<Cart> carts);
    /**
     * delete cart
     *
     * @param cart Cart model
     * @return
     */
    int deleteCart(Cart cart);

    /**批量删除某一客户的一批商品
     * @param customerId
     * @param goodsIdArr
     * @param staffId
     */
    void deletesCartByStaffIdAndGoodsIdArr(@Param("customerId") String customerId,
                                           @Param("goodsIdArr")List<String> goodsIdArr,
                                           @Param("staffId") String staffId);

    /**查询当前员工某一客户的购物车
     * @param staffId
     * @param customerId
     * @return
     */
    List<CustomerCartVO> selectCartByCustomerId(@Param("staffId") String staffId,
                                                @Param("customerId")String customerId);


}
