package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Cart;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.DeleteCartPojo;
import com.ywkj.ktyunxiao.model.vo.CustomerCartVO;
import com.ywkj.ktyunxiao.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/5 14:50
 */
@RestController
@RequestMapping("/api/cart")
@Api(description = "商品购物车")
public class CartController {


    @Autowired
    private CartService cartService;


    @ApiOperation(value = "添加购物车信息")
    @PostMapping(value = "")
    public JsonResult insertCart(@RequestBody() List<Cart> carts) {

        int i;
        if (carts.size() == 1) {
            if (carts.get(0).getQuantity() == 0) {
                i = cartService.deleteCart(carts.get(0));
            } else {
                i = cartService.insertList(carts);
            }
        } else {
            i = cartService.insertList(carts);
        }
        return i > 0 ? JsonResult.success() : JsonResult.error(Code.ERROR);
    }

    @ApiOperation(value = "查询购物车信息")
    @GetMapping(value = "")
    public JsonResult selectCartByCustomerId(@ApiIgnore @RequestAttribute("staff") Staff staff,
                                             @RequestParam(value = "customerId", required = false) String customerId) {
        List<CustomerCartVO> customerCartVOS = cartService.selectCartByCustomerId(staff.getStaffId(), customerId);
        return JsonResult.success(customerCartVOS);
    }


    @ApiOperation(value = "批量删除购物车信息")
    @PostMapping(value = "/all")
    public JsonResult deleteAllCartByCustomerId(@ApiIgnore @RequestAttribute("staff") Staff staff
            , @RequestBody List<DeleteCartPojo> dcp) {

        dcp.forEach(d -> {
            cartService.deletesCartByStaffIdAndGoodsIdArr(d.getCustomerId(), d.getGoodsIds(), staff.getStaffId());
        });
        return JsonResult.success();
    }
}
