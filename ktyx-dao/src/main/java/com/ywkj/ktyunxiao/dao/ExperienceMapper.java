package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Experience;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/16 17:09
 */
@Component
public interface ExperienceMapper {
    /**
     * 添加
     * @param experience
     * @return
     */
    int insert(Experience experience);

    /**
     * 分页查询经验值流水根据职员Id
     * @param companyId
     * @param staffId
     * @param firstNumber
     * @param lastNumber
     * @return
     */
    List<Experience> selectLimit(@Param("companyId") String companyId, @Param("staffId") String staffId, @Param("firstNumber") int firstNumber, @Param("lastNumber") int lastNumber);

    /**
     * 分页查询经验值流水数量
     * @param companyId
     * @param staffId
     * @return
     */
    int selectLimitCount(@Param("companyId") String companyId, @Param("staffId") String staffId);
}
