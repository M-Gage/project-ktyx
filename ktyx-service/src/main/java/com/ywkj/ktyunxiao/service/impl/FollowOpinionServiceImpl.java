package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.annotation.Exp;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.ExpType;
import com.ywkj.ktyunxiao.dao.FollowOpinionMapper;
import com.ywkj.ktyunxiao.model.FollowOpinion;
import com.ywkj.ktyunxiao.model.pojo.FollowOpinionPojo;
import com.ywkj.ktyunxiao.service.FollowOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/09 16:05
 */
@Service
public class FollowOpinionServiceImpl implements FollowOpinionService {

    private final int SUCCESS_ROW = (int) SystemConfig.successRow;

    @Autowired
    private FollowOpinionMapper followOpinionMapper;

    @Exp(ExpType.INSERT_FOLLOW_OPINION)
    @Override
    public void insert(FollowOpinionPojo followOpinionPojo, int opinion) {
        followOpinionMapper.insert(followOpinionPojo);
    }

    @Override
    public List<FollowOpinion> selectByFollowId(String companyId, String followId) {
        return followOpinionMapper.selectByFollowId(companyId,followId);
    }

    @Exp(ExpType.DELETE_FOLLOW_OPINION)
    @Override
    public boolean deleteByFollowIdAndStaffId(String companyId, String followId, String staffId, String followStaffId, int opinion) {
        return followOpinionMapper.deleteByFollowIdAndStaffId(companyId, followId, staffId) == SUCCESS_ROW;
    }
}
