package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.dao.ExperienceMapper;
import com.ywkj.ktyunxiao.model.Experience;
import com.ywkj.ktyunxiao.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/07/16 17:12
 */
@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final int SUCCESS_ROW = (int) SystemConfig.successRow;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Override
    public boolean insert(Experience experience) {
        return experienceMapper.insert(experience) == SUCCESS_ROW;
    }

    @Override
    public Map<String, Object> selectLimit(String companyId, String staffId, int firstNumber, int lastNumber) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("rows", experienceMapper.selectLimit(companyId, staffId,firstNumber,lastNumber));
        map.put("total", experienceMapper.selectLimitCount(companyId, staffId));
        return map;
    }
}
