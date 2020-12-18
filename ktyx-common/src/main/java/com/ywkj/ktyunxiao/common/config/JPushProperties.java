package com.ywkj.ktyunxiao.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiWenHao
 * @date 2018/07/19 13:38
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jiguang")
public class JPushProperties {
    private String appKey;
    private String secret;
    private long timeToLive;
}
