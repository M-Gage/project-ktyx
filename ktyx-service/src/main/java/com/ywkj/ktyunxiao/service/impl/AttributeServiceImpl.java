package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.AttributeMapper;
import com.ywkj.ktyunxiao.model.Attribute;
import com.ywkj.ktyunxiao.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/28 16:32
 */
@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeMapper attributeMapper;

    @Override
    public Attribute insertAttribute(Attribute attribute) {
        attributeMapper.insertAttribute(attribute);
        return attribute;
    }

    @Override
    public List<Attribute> selectAllGoodAttribute(Integer isNonstandard, String companyId) {
        return attributeMapper.selectAll(isNonstandard,companyId);
    }

    @Override
    public List<Attribute> selectGoodAttribute(Attribute attribute) {
        return null;
    }
}
