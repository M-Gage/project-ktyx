package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Grade;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/17 10:40
 */
public interface GradeService {

    List<Grade> selectCompanyId(String companyId);

    boolean insert(Grade grade);

    boolean selectMaxExperience(String companyId,int experience);

}
