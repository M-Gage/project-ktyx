package com.ywkj.ktyunxiao.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiWenHao
 * @date 2018/5/22 16:25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String header = "Authorization";
    private String secret = "jxywkj@820";
    private long expiration = 604800L;
}
