package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.FollowOpinion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/09 16:06
 */
@Component
public interface FollowOpinionMapper {
    /**
     * 添加意见
     * @param followOpinion
     * @return
     */
    int insert(FollowOpinion followOpinion);

    /**
     * 根据跟进Id获取意见列表
     * @param companyId
     * @param followId
     * @return
     */
    List<FollowOpinion> selectByFollowId(@Param("companyId") String companyId, @Param("followId") String followId);

    /**
     * 根据公司id跟进id和职员Id删除意见
     * @param companyId
     * @param followId
     * @param staffId
     * @return
     */
    int deleteByFollowIdAndStaffId(@Param("companyId") String companyId, @Param("followId") String followId, @Param("staffId") String staffId);
}
