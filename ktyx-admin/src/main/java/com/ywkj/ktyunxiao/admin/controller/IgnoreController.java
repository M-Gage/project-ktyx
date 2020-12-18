package com.ywkj.ktyunxiao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * 无需过滤的控制器
 *
 * @author MiaoGuoZhu
 * @date 2018/1/5 0005 14:56
 */
@Controller
public class IgnoreController {

    @GetMapping("/goods/{id}")
    public ModelAndView test(@PathVariable("id") int id){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id",id);
        modelAndView.setViewName("goods_info");
        return modelAndView;
    }
    @GetMapping("/staffInfo/{staffId}")
    public ModelAndView staffInfo(@PathVariable("staffId") String staffId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id",staffId);
        modelAndView.setViewName("staff_info");
        return modelAndView;
    }

    @GetMapping("/order/{orderId}")
    public ModelAndView test(@PathVariable("orderId") String orderId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderId",orderId);
        modelAndView.setViewName("order_info");
        return modelAndView;
    }
}
