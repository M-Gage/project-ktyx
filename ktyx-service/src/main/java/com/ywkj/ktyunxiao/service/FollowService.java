package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Follow;
import com.ywkj.ktyunxiao.model.Staff;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/07/03 15:34
 */
public interface FollowService {

    void insert(Follow follow, MultipartFile[] multipartFileArray);

    Map<String,Object> selectLimit(Staff staff, String customerId, int firstNumber, int pageSize);

    String selectStaffIdByPrimaryId(String followId, String companyId);
}
