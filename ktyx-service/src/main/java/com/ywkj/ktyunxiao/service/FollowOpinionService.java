package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.FollowOpinion;
import com.ywkj.ktyunxiao.model.pojo.FollowOpinionPojo;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/09 16:05
 */
public interface FollowOpinionService {

    void insert(FollowOpinionPojo followOpinionPojo, int opinion);

    List<FollowOpinion> selectByFollowId(String companyId, String followId);

    boolean deleteByFollowIdAndStaffId(String companyId, String followId, String staffId, String followStaffId,int opinion);

}
