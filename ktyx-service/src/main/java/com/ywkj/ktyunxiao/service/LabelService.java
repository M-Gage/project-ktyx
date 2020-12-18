package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Label;
import com.ywkj.ktyunxiao.model.Staff;

import java.util.List;
import java.util.Map;

/**
 * @author LiWenHao
 * @date 2018/6/7 15:03
 */
public interface LabelService {

    List<Label> selectList(Staff staff);

    boolean insert(Label label);

    boolean selectLabelName(String companyId, String labelName, String staffId);

    boolean selectAllLabelName(String companyId, String labelName);

    List<Label> selectNotPrivate(String companyId);

    void insertNotPrivate(Label label);

    Map<String,Object> selectLimit(String companyId, int firstNum, int lastNumber);

    void updateByPrimaryId(Label label);
}
