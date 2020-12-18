package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.FollowComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/10 17:25
 */
@Component
public interface FollowCommentMapper {

    /**
     * 插入回复
     * @param followComment
     * @return
     */
    int insert(FollowComment followComment);

    /**
     * 根据跟进Id获取评论列表
     * @param companyId
     * @param followId
     * @return
     */
    List<FollowComment> selectByFollowId(@Param("companyId") String companyId, @Param("followId") String followId);

    /**
     * 根据回复Id获取职员Id
     * @param replyCommentId
     * @param companyId
     * @return
     */
    String selectStaffIdByPrimaryId(@Param("replyCommentId") int replyCommentId, @Param("companyId") String companyId);

}
