package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.CartMapper;
import com.ywkj.ktyunxiao.model.Cart;
import com.ywkj.ktyunxiao.model.vo.CustomerCartVO;
import com.ywkj.ktyunxiao.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 15:39
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public int insertList(List<Cart> carts) {
        return cartMapper.insertList(carts);
    }

    @Override
    public int deleteCart(Cart cart) {
        return cartMapper.deleteCart(cart);
    }

    @Override
    public List<CustomerCartVO> selectCartByCustomerId(String staffId, String customerId) {
        return cartMapper.selectCartByCustomerId(staffId, customerId);
    }

    @Override
    public void deletesCartByStaffIdAndGoodsIdArr(String customerId, List<String> goodsIdArr, String staffId) {

        cartMapper.deletesCartByStaffIdAndGoodsIdArr( customerId, goodsIdArr, staffId);
    }
}
