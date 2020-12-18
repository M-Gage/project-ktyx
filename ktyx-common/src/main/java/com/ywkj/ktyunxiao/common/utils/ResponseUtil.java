package com.ywkj.ktyunxiao.common.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LiWenHao
 * @date 2018/5/22 16:35
 */
public class ResponseUtil {

    public static void Write(HttpServletRequest request, HttpServletResponse response , Object o)  {
        PrintWriter writer = null;
        try {
            if(isHtml(request)){
                response.sendRedirect("/");
            }
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(o));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static boolean isHtml(HttpServletRequest request){
        String accept = request.getHeader("accept");
        return accept.contains("text/html");
    }
}
