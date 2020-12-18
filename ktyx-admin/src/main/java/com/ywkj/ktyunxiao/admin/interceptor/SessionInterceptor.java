package com.ywkj.ktyunxiao.admin.interceptor;

import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiWenHao
 * @date 2018/5/22 10:33
 */
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StaffPojo staff= (StaffPojo) request.getSession().getAttribute("staff");
        if(staff != null && staff.getStaffName() != null){
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }

}
