package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.AttributeValue;
import com.ywkj.ktyunxiao.model.Staff;

import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/29 17:08
 */

public interface AttributesValueService {

    /**
     * 查询全部属性值
     * @param companyId
     * @param attributeId
     * @return
     */
    List<AttributeValue> selectAttributeValuesByAttributeId(String companyId, Integer attributeId);

    /**
     * 根据属性id插入属性值
     * @param attributeValues
     * @param attributeId
     * @param staff
     * @return
     */
    int insertAttributeValue(String attributeValues, Integer attributeId, Staff staff);


}
