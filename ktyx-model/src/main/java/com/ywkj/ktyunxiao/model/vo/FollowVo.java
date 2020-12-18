package com.ywkj.ktyunxiao.model.vo;

import com.ywkj.ktyunxiao.model.Follow;
import com.ywkj.ktyunxiao.model.FollowComment;
import com.ywkj.ktyunxiao.model.FollowOpinion;
import com.ywkj.ktyunxiao.model.FollowPic;
import lombok.Data;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/09 10:49
 */
@Data
public class FollowVo extends Follow {

    List<FollowPic> followPicList;
    List<FollowOpinion> followOpinionList;
    List<FollowComment> followCommentList;

}
