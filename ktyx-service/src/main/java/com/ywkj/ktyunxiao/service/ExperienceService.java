package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Experience;

import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/07/16 17:12
 */
public interface ExperienceService {

    boolean insert(Experience experience);

    Map<String,Object> selectLimit(String companyId,String staffId,int firstNumber,int lastNumber);
}
