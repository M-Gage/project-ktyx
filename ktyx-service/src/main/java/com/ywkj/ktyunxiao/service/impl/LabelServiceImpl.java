package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.exception.NoticeException;
import com.ywkj.ktyunxiao.dao.LabelMapper;
import com.ywkj.ktyunxiao.model.Label;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.CustomerLabelService;
import com.ywkj.ktyunxiao.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/6/7 15:03
 */
@Slf4j
@Service
public class LabelServiceImpl implements LabelService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private CustomerLabelService customerLabelService;

    @Override
    public List<Label> selectList(Staff staff) {
        return labelMapper.selectList(staff);
    }

    @Override
    public boolean insert(Label label) {
        return labelMapper.insert(label) == SUCCESS_INT;
    }

    @Override
    public boolean selectLabelName(String companyId, String labelName, String staffId) {
        return labelMapper.selectLabelName(companyId,labelName,staffId) > 0;
    }

    @Override
    public boolean selectAllLabelName(String companyId, String labelName) {
        List<Label> labelList = labelMapper.selectLabelNameByCompanyId(companyId, labelName);
        //查不到
        if (labelList.size() == 0) {
            return false;
        }
        //查到并且第一个为公有
        return labelList.size() == 1 && labelList.get(0).getIsPrivate() == 0;
    }

    @Override
    public List<Label> selectNotPrivate(String companyId) {
        return labelMapper.selectNotPrivate(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertNotPrivate(Label label) {
        if (!upgradeNotPrivate(label)){
            labelMapper.insert(label);
        }
    }

    @Override
    public Map<String, Object> selectLimit(String companyId, int firstNumber, int lastNumber) {
        Map<String,Object> map = new HashMap<>(16);
        map.put("rows", labelMapper.selectLimit(companyId,firstNumber,lastNumber));
        map.put("total", labelMapper.selectLimitCount(companyId));
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByPrimaryId(Label label) {
        //校验
        if (label.getIsPrivate() == 0 && upgradeNotPrivate(label)) {
            return;
        }
        //私有
        if (selectAllLabelName(label.getCompanyId(), label.getLabelName())) {
            throw new CheckException("标签名称重复");
        }
        labelMapper.updateByPrimaryId(label);
    }

    /**
     * 提升为公有标签
     * @param label
     */
    private boolean upgradeNotPrivate (Label label) {
        if (selectAllLabelName(label.getCompanyId(), label.getLabelName())){
            throw new CheckException("标签名称重复");
        }
        List<Label> labelList = labelMapper.selectLabelNameByCompanyId(label.getCompanyId(), label.getLabelName());
        List<Integer> labelIdList = new ArrayList<>();
        //表示找到私有在用此名称
        if (labelList.size() != 0) {
            int firstLabelId = 0 ;
            //如果只有一个的话直接提升为公有标签
            if (labelList.size() == 1) {
                firstLabelId = labelList.get(0).getLabelId();
            } else {
                //如果有多个
                for (Label l : labelList) { labelIdList.add(l.getLabelId()); }
                firstLabelId = labelIdList.get(0);
                //修改所有的标签为第一个标签的Id
                customerLabelService.updateLabelId(labelIdList, firstLabelId, label.getCompanyId());
                //删除除第一个以为的标签
                labelIdList.remove(0);
                labelMapper.deleteListByPrimaryId(labelIdList, label.getCompanyId());
            }
            if (firstLabelId == 0) {
                log.error("拿不到需要提升公有标签的Id");
                throw new NoticeException("插入失败");
            }
            labelMapper.updatePrivate(firstLabelId, label.getStaffId(), label.getStaffName(),label.getCompanyId());
            return true;
        }
        return false;
    }
}
