package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.ExperienceConfig;

/**
 * @author LiWenHao
 * @date 2018/07/16 15:16
 */
public interface ExperienceConfigService {

    ExperienceConfig selectByCompanyId(String companyId);

    void updateByCompanyId(ExperienceConfig experienceConfig);
}
