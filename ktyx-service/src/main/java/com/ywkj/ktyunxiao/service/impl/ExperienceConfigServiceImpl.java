package com.ywkj.ktyunxiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.CacheName;
import com.ywkj.ktyunxiao.dao.ExperienceConfigMapper;
import com.ywkj.ktyunxiao.model.ExperienceConfig;
import com.ywkj.ktyunxiao.service.ExperienceConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LiWenHao
 * @date 2018/07/16 15:17
 */
@Slf4j
@Service
public class ExperienceConfigServiceImpl implements ExperienceConfigService {

    private final int SUCCESS_ROW = (int) SystemConfig.successRow;

    @Resource
    private CacheManager cacheManager;

    @Autowired
    private ExperienceConfigMapper experienceConfigMapper;

    @Override
    public ExperienceConfig selectByCompanyId(String companyId) {
        return experienceConfigMapper.selectByCompanyId(companyId);
    }

    @Override
    public void updateByCompanyId(ExperienceConfig experienceConfig) {
        if (experienceConfigMapper.updateByCompanyId(experienceConfig) == SUCCESS_ROW) {
            Cache cache = cacheManager.getCache(CacheName.EXPERIENCE.getCacheName());
            log.info("更新缓存经验值");
            cache.put(experienceConfig.getCompanyId(), JSON.toJSONString(selectByCompanyId(experienceConfig.getCompanyId())));
        }
    }
}
