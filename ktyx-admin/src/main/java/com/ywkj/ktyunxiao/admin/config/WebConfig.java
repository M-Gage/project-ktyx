package com.ywkj.ktyunxiao.admin.config;

import com.ywkj.ktyunxiao.admin.filter.TokenFilter;
import com.ywkj.ktyunxiao.admin.interceptor.SessionInterceptor;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author LiWenHao
 * @date 2018/5/22 0022 10:01
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.load-Path}")
    private String loadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {


        registry.addViewController("/test").setViewName("/test");

        /*
         * 404
         */
        registry.addViewController("/404").setViewName("/exception/not_found");

        /*
         * 等级管理
         */
        registry.addViewController("/levelManager").setViewName("level_manager");

        /*
         * 地图报表
         */
        registry.addViewController("/mapChart").setViewName("map_chart");

        /*
         * 登录和主页
         */
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");

        /*
         * 职员
         */
        registry.addViewController("/staffManager").setViewName("staff_manager");
        registry.addViewController("/staffContrast").setViewName("staff_contrast");
        registry.addViewController("/staffAnalyse").setViewName("staff_analyse");
        registry.addViewController("/staffInfo").setViewName("staff_info");

        /*
         * 客户
         */
        registry.addViewController("/customerAdd").setViewName("customer_add");
        registry.addViewController("/customerManager").setViewName("customer_manager");
        registry.addViewController("/customerAnalyse").setViewName("customer_analyse");
        registry.addViewController("/customerInfo").setViewName("customer_info");

        /*
         * 部门
         */
        registry.addViewController("/deptManager").setViewName("dept_manager");

        /*
         * 跟进
         */
        registry.addViewController("/followupManager").setViewName("followup_manage");

        /*
         * 权限
         */
        registry.addViewController("/authorityManager").setViewName("authority_manager");

        /*
         * 标签
         */
        registry.addViewController("/labelManager").setViewName("label_manager");

        /*
         * 商品
         *
         */
        registry.addViewController("/goodsAdd").setViewName("goods_add");
        registry.addViewController("/goodsType").setViewName("goods_type");
        registry.addViewController("/goodsList").setViewName("goods_list");
        registry.addViewController("/goodsUpdate").setViewName("goods_update");
        registry.addViewController("/goodsSaleContrast").setViewName("goods_contrast");

        /*
         * 订单
         */
        registry.addViewController("/orderList").setViewName("order_list");
        registry.addViewController("/orderList").setViewName("order_list");
        registry.addViewController("/orderAnalyse").setViewName("order_statistics");

        /*
         * 日程
         */
        registry.addViewController("/scheduleManager").setViewName("schedule");
        registry.addViewController("/routePlan").setViewName("route_plan");

        /*
        * 其他报表
        */
        registry.addViewController("/areaAnalyse").setViewName("area_analyse");

        /*
        * 公告
        */
        registry.addViewController("/announcementAdd").setViewName("announcement_add");
        registry.addViewController("/announcementManager").setViewName("announcement_manager");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**","/404", "/static/**", "/login","/staff/login","/staff/logout")
                .excludePathPatterns("/swagger-resources/**",SystemConfig.resourceName + "**");
    }

    /**
     * Swagger2和图片资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/*").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler(SystemConfig.resourceName + "**").addResourceLocations(loadPath);
    }



    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }


}
