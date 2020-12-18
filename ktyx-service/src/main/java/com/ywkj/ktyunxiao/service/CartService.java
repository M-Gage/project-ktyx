package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Cart;
import com.ywkj.ktyunxiao.model.vo.CustomerCartVO;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 15:02
 */
public interface CartService {

    /**
     * insert cart information
     * @param carts Carts
     * @return success number
     */
    int insertList(List<Cart> carts);
    /**delete cart
     * @param cart Cart model
     * @return
     */
    int deleteCart(Cart cart);
    /**
     * select cart collection by staff primaryKey and customer primaryKey
     * @param staffId staff primaryKey
     * @param customerId customer primaryKey
     * @return Cart collection
     */
    List<CustomerCartVO> selectCartByCustomerId(String staffId, String customerId);


    /**批量删除某一客户的一批商品
     * @param customerId
     * @param goodsArr
     * @param staffId
     * @return
     */
    void deletesCartByStaffIdAndGoodsIdArr(String customerId, List<String> goodsArr,String staffId);



}
