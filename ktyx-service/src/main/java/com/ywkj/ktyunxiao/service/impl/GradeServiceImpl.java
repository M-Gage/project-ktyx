package com.ywkj.ktyunxiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.CacheName;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.utils.JPushUtil;
import com.ywkj.ktyunxiao.common.utils.ListUtil;
import com.ywkj.ktyunxiao.dao.GradeMapper;
import com.ywkj.ktyunxiao.model.Grade;
import com.ywkj.ktyunxiao.service.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/17 10:50
 */
@Slf4j
@Service
public class GradeServiceImpl implements GradeService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Resource
    private CacheManager cacheManager;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private JPushUtil jPushUtil;

    @Override
    public List<Grade> selectCompanyId(String companyId) {
        Cache cache = cacheManager.getCache(CacheName.GRADE.getCacheName());
        List<Grade> gradeList = cache.get(companyId, ArrayList.class);
        if (ListUtil.isNotEmpty(gradeList)) {
            cache.put(companyId,gradeList);
        } else {
            gradeList = gradeMapper.selectCompanyId(companyId);
        }
        return gradeList;
    }

    @Override
    public boolean insert(Grade grade) {
        grade.setGradeName("LV" + (gradeMapper.selectCount(grade.getCompanyId()) + 1));
        if (!selectMaxExperience(grade.getCompanyId(),grade.getGradeExp())){
            throw new CheckException("经验值必须大于上一级");
        }
        boolean b = gradeMapper.insert(grade) == SUCCESS_INT;
        if (b) {
            Cache cache = cacheManager.getCache(CacheName.GRADE.getCacheName());
            log.info("更新等级缓存");
            List<Grade> gradeList = gradeMapper.selectCompanyId(grade.getCompanyId());
            cache.put(grade.getCompanyId(), gradeList);
            jPushUtil.send(jPushUtil.message("GRADE", JSON.toJSONString(gradeList), grade.getCompanyId()));
        }
        return b;
    }

    @Override
    public boolean selectMaxExperience(String companyId, int experience) {
        return gradeMapper.selectMaxExperience(companyId) < experience;
    }
}
