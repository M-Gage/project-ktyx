package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.utils.JPushUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.FollowCommentMapper;
import com.ywkj.ktyunxiao.model.FollowComment;
import com.ywkj.ktyunxiao.service.FollowCommentService;
import com.ywkj.ktyunxiao.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/10 17:24
 */
@Service
public class FollowCommentServiceImpl implements FollowCommentService {

    @Autowired
    private FollowCommentMapper followCommentMapper;

    @Autowired
    private FollowService followService;

    @Autowired
    private JPushUtil jPushUtil;

    private final int SUCCESS_ROW = (int) SystemConfig.successRow;

    @Override
    public boolean insert(FollowComment followComment) {
        if (followCommentMapper.insert(followComment) == SUCCESS_ROW) {
            String sendId = "";
            if (followComment.getReplyCommentId() == null) {
                sendId = followService.selectStaffIdByPrimaryId(followComment.getFollowId(), followComment.getCompanyId());
                if (!followComment.getStaffId().equals(sendId) && StringUtil.isNotEmpty(sendId)){
                    jPushUtil.send(jPushUtil.alis(followComment.getStaffName() + "回复了你的跟进", sendId));
                }
            } else {
                sendId = followCommentMapper.selectStaffIdByPrimaryId(followComment.getReplyCommentId(), followComment.getCompanyId());
                jPushUtil.send(jPushUtil.alis(followComment.getStaffName() + "回复了你的跟进", sendId));
            }
            return true;
        }
        return false;
    }

    @Override
    public List<FollowComment> selectByFollowId(String companyId, String followId) {
        return followCommentMapper.selectByFollowId(companyId, followId);
    }
}
