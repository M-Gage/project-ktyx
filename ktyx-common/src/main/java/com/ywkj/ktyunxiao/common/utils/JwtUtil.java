package com.ywkj.ktyunxiao.common.utils;

import com.ywkj.ktyunxiao.common.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/5/4 9:38
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 创建token
     * @return
     */
    public String createToken(Map<String, Object> claims, String subject){
        Date createDate = new Date();
        Date expirationDate = new Date(createDate.getTime() + jwtProperties.getExpiration() * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,jwtProperties.getSecret())
                .compact();
    }

    /**
     * 获取payload
     * @param token token
     * @return
     */
    public Claims getTokenPayload(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取参数
     * @param token
     * @param s
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getValue(String token,String s,Class<T> clazz){
        return getTokenPayload(token).get(s,clazz);
    }
}
