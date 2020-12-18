package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.annotation.Exp;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.ExpType;
import com.ywkj.ktyunxiao.common.enums.FileType;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.exception.NoticeException;
import com.ywkj.ktyunxiao.common.utils.FileUtil;
import com.ywkj.ktyunxiao.common.utils.ImageUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.FollowMapper;
import com.ywkj.ktyunxiao.model.Follow;
import com.ywkj.ktyunxiao.model.FollowPic;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.CustomerService;
import com.ywkj.ktyunxiao.service.FollowPicService;
import com.ywkj.ktyunxiao.service.FollowService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/07/03 15:37
 */
@Service
public class FollowServiceImpl implements FollowService {

    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private FollowPicService followPicService;

    @Autowired
    private CustomerService customerService;

    @Exp(value = ExpType.INSERT_FOLLOW)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(Follow follow, MultipartFile[] multipartFileArray) {
        List<FollowPic> followPicList = new ArrayList<>();
        if(multipartFileArray.length > 0) {
            for (MultipartFile file : multipartFileArray) {
                String fileSuffix = FileUtil.getFileSuffix(file.getOriginalFilename());
                FileType fileType = FileType.val(fileSuffix);
                Map<String, Object> uploadResult = null;
                //非法后缀的文件
                if (fileType != null) {
                    switch (fileType){
                        case MP3:
                            uploadResult = FileUtil.fileUpload(file);
                            if ((boolean) uploadResult.get("success")) {
                                follow.setVoicePath(SystemConfig.resourceName + uploadResult.get("newFileName"));
                            } else {
                                throw new NoticeException("文件上传失败！");
                            }
                            break;
                        case JPG:
                        case PNG:
                            uploadResult = FileUtil.fileUpload(file);
                            if ((boolean) uploadResult.get("success")) {
                                String fileName = uploadResult.get("newFileName").toString();
                                String fileUrl = uploadResult.get("fileUrl").toString();
                                //缩略图
                                String previewName = ImageUtil.createPreviewImg(fileUrl + fileName);
                                FollowPic followPic = new FollowPic(follow.getFollowId(),SystemConfig.resourceName  + fileName, SystemConfig.resourceName + "preview/" + previewName,follow.getCompanyId());
                                followPicList.add(followPic);
                            }
                            break;
                        default:
                    }
                }
            }
        }
        if (StringUtil.isEmpty(follow.getContent()) && StringUtil.isEmpty(follow.getVoicePath()) && followPicList.size() == 0) {
            throw new CheckException("文件,图片,语音不能同时为空！");
        }
        followMapper.insert(follow);
        if (!customerService.updateLastFollowTime(follow.getCustomerId(),follow.getCompanyId())){
            throw new RuntimeException("最后跟进时间修改失败！");
        }
        if (followPicList.size() > 0) {
            if(!followPicService.insertList(followPicList)){
                throw new RuntimeException("图片添加失败！");
            }
        }
    }

    @Override
    public Map<String, Object> selectLimit(Staff staff, String customerId, int firstNumber, int pageSize) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("rows", followMapper.selectLimit(staff, customerId,firstNumber, pageSize));
        map.put("total", followMapper.selectLimitCount(staff,customerId));
        return map;
    }

    @Override
    public String selectStaffIdByPrimaryId(String followId, String companyId) {
        return followMapper.selectStaffIdByPrimaryId(followId,companyId);
    }
}
