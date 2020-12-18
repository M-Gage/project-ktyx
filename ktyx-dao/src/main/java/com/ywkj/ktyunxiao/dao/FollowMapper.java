package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Follow;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.vo.FollowVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/03 17:56
 */
@Component
public interface FollowMapper {

    /**
     * 插入跟进
     * @param follow
     * @return
     */
    int insert(Follow follow);

    /**
     * 分页获取跟进集合
     * @param staff
     * @param firstNumber
     * @param lastNumber
     * @return
     */
    List<FollowVo> selectLimit(@Param("pojo") Staff staff, @Param("customerId") String customerId, @Param("firstNumber") int firstNumber, @Param("lastNumber") int lastNumber);

    /**
     * 获取跟进的数量
     * @param staff
     * @return
     */
    int selectLimitCount(@Param("pojo") Staff staff, @Param("customerId") String customerId);

    /**
     * 根据跟进Id获取职员Id
     * @param followId
     * @param companyId
     * @return
     */
    String selectStaffIdByPrimaryId(@Param("followId") String followId, @Param("companyId") String companyId);

}


