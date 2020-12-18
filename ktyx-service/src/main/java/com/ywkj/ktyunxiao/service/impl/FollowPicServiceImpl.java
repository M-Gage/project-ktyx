package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.FollowPicMapper;
import com.ywkj.ktyunxiao.model.FollowPic;
import com.ywkj.ktyunxiao.service.FollowPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/03 18:02
 */
@Service
public class FollowPicServiceImpl implements FollowPicService {

    @Autowired
    private FollowPicMapper followPicMapper;

    @Override
    public boolean insertList(List<FollowPic> followPicList) {
        return followPicMapper.insertList(followPicList) == followPicList.size();
    }
}
