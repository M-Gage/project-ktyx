package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Grade;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/17 10:40
 */
@Component
public interface GradeMapper {

    /**
     * 根据公司id获取
     * @param companyId
     * @return
     */
    List<Grade> selectCompanyId(String companyId);

    /**
     * 插入等级
     * @param grade
     * @return
     */
    int insert(Grade grade);

    /**
     * 根据公司Id获取最高经验值
     * @param companyId
     * @return
     */
    int selectMaxExperience(String companyId);

    /**
     * 获取等级数量
     * @param companyId
     * @return
     */
    int selectCount(String companyId);

}
