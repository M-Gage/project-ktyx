package com.ywkj.ktyunxiao.admin.filter;

import com.ywkj.ktyunxiao.common.enums.CacheName;
import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.enums.TokenAttribute;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.JwtUtil;
import com.ywkj.ktyunxiao.common.utils.ResponseUtil;
import com.ywkj.ktyunxiao.common.config.JwtProperties;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Staff;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiWenHao
 * @date 2018/5/22 10:02
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Resource
    private CacheManager cacheManager;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String APP_REQUEST_PREFIX = "/api";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String APP_LOGIN_PATH = "/api/staff/login";
    private static final String WEB_LOGIN_PATH = "/staff/login";
    private static final String WEB_LOGOUT_PATH = "/staff/logout";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(jwtProperties.getHeader());
        String requestUrl = request.getServletPath();
        log.info("{} {} ",request.getMethod(),request.getRequestURL());
        log.info("来源  {} ",request.getHeader("User-Agent"));
        if(requestUrl.equals(APP_LOGIN_PATH) || requestUrl.equals(WEB_LOGIN_PATH) || requestUrl.equals(WEB_LOGOUT_PATH)){
            chain.doFilter(request,response);
            return;
        }
        if(requestUrl.length() > 3 && requestUrl.substring(0,4).equals(APP_REQUEST_PREFIX)){
            if(token != null && token.startsWith(TOKEN_PREFIX)){
                try {
                    //缓存对象
                    Cache tokenCache = cacheManager.getCache(CacheName.TOKEN.getCacheName());
                    String authToken = token.substring(TOKEN_PREFIX.length());
                    Claims tokenPayload = jwtUtil.getTokenPayload(authToken);
                    String companyId = tokenPayload.get(TokenAttribute.COMPANY_ID.getValue(), String.class);
                    String staffId = tokenPayload.get(TokenAttribute.STAFF_ID.getValue(), String.class);
                    Integer roleId = tokenPayload.get(TokenAttribute.ROLE_ID.getValue(), Integer.class);
                    String staffName = tokenPayload.get(TokenAttribute.STAFF_NAME.getValue(), String.class);
                    String deptId = tokenPayload.get(TokenAttribute.DEPT_ID.getValue(), String.class);
                    Integer isDeptManager = tokenPayload.get(TokenAttribute.IS_DEPT_MANAGER.getValue(), Integer.class);
                    String cacheToken = tokenCache.get(staffId, String.class);
                    if (StringUtil.isNotEmpty(cacheToken)) {
                        //缓存中和传入的token一致
                        if (!cacheToken.equals(authToken)){
                            ResponseUtil.Write(request, response, JsonResult.error(Code.NOT_LOGIN));
                            return;
                        }
                    } else {
                        //缓存中为空则存进缓存
                        log.info("存进缓存 key:{} value:{}",staffId, authToken);
                        tokenCache.put(staffId,authToken);
                    }
                    request.setAttribute("staff",new Staff(staffId,companyId,roleId,staffName,deptId,isDeptManager));
                    chain.doFilter(request,response);
                    return;
                } catch (ExpiredJwtException e) {
                    ResponseUtil.Write(request, response, JsonResult.error(Code.TOKEN_FAIL));
                } catch (MalformedJwtException e){
                    ResponseUtil.Write(request, response, JsonResult.error(Code.TOKEN_ERROR));
                }
            }
            ResponseUtil.Write(request, response, JsonResult.error(Code.NOT_LOGIN));
            return;
        }
        chain.doFilter(request,response);
    }
}
