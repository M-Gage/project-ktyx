package com.ywkj.ktyunxiao.admin.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

/**
 * 错误页面
 * @author LiWenHao
 * @date 2018/06/29 15:30
 */
@Controller
public class ErrorEndpoint implements ErrorController {

    private static final String ERROR_PATH = "/404";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
