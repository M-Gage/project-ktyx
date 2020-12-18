package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.AttributeValueMapper;
import com.ywkj.ktyunxiao.model.AttributeValue;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.AttributesValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/30 9:29
 */
@Service
public class AttributesValueServiceImpl implements AttributesValueService {


    @Autowired
    private AttributeValueMapper attributeValueMapper;
    @Override
    public List<AttributeValue> selectAttributeValuesByAttributeId(String companyId, Integer attributeId) {
        return attributeValueMapper.selectAttributeValuesByAttributeId(companyId,attributeId);
    }

    @Override
    public int insertAttributeValue(String attributeValues, Integer attributeId, Staff staff) {
        return attributeValueMapper.insertAttributeValue(attributeValues,attributeId,staff);
    }


/*
    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Override
    public List<AttributeValue> selectAll(String companyId) {
        return goodAttributesValMapper.selectAll(companyId);
    }

    @Override
    public List<Map<String, Object>> getMaps(List<AttributeValue> root) {

    }

    private Map<String, Object> isExists(List<Map<String, Object>> result, AttributeValue key) {
        for (Map<String, Object> map : result) {
            if (map.get("goodsAttributesId").equals(key.getAttributeId())) {
                return map;
            }
        }
        return null;
    }*/
}
