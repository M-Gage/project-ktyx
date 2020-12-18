package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.FollowComment;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/10 17:20
 */
public interface FollowCommentService {

    boolean insert(FollowComment followComment);

    List<FollowComment> selectByFollowId(String companyId, String followId);
}
