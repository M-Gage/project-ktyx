package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.ExperienceConfig;
import org.springframework.stereotype.Component;

/**
 * @author LiWenHao
 * @date 2018/07/16 15:10
 */
@Component
public interface ExperienceConfigMapper {

    /**
     * 根据公司id获取经验值配置
     * @param companyId
     * @return
     */
    ExperienceConfig selectByCompanyId(String companyId);

    /**
     * 修改经验值配置
     * @param experienceConfig
     * @return
     */
    int updateByCompanyId(ExperienceConfig experienceConfig);
}
