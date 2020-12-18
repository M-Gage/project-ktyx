package com.ywkj.ktyunxiao.service.impl;

import com.alibaba.fastjson.JSON;
import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.utils.JPushUtil;
import com.ywkj.ktyunxiao.dao.CompanyMapper;
import com.ywkj.ktyunxiao.model.Company;
import com.ywkj.ktyunxiao.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    //成功条数
    private final int SUCCESS_INT = SystemConfig.successRow;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JPushUtil jPushUtil;

    @Override
    public Company selectMapKeyWordByPrimary(String companyId) {
        return companyMapper.selectMapKeyWordByPrimary(companyId);
    }

    @Override
    public boolean updateMapKeyWord(String poi, String keyWord, String companyId) {
        boolean b = companyMapper.updateMapKeyWord(poi, keyWord, companyId) == SUCCESS_INT;
        if (b) {
            jPushUtil.send(jPushUtil.message("COMPANY", JSON.toJSONString(JSON.toJSONString(companyMapper.selectMapKeyWordByPrimary(companyId))), companyId));
        }
        return b;
    }
}
