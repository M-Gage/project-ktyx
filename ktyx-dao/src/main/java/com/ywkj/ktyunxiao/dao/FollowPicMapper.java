package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.FollowPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/07/03 18:01
 */
@Component
public interface FollowPicMapper {

   int insertList(@Param("followPicList") List<FollowPic> followPicList);
}
