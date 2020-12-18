package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.AttributeValue;
import com.ywkj.ktyunxiao.model.Staff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/30 10:44
 */
@Component
public interface AttributeValueMapper {
    /**
     * 根据属性id查询属性值
     * @param companyId
     * @param attributeId
     * @return
     */
    List<AttributeValue> selectAttributeValuesByAttributeId(@Param("companyId") String companyId,
                                                            @Param("attributeId")  Integer attributeId);

    /**插入属性值
     * @param attributeValue
     * @param attributeId
     * @param staff
     * @return
     */
    int insertAttributeValue(@Param("attributeValue")String attributeValue,
                             @Param("attributeId")Integer attributeId,
                             @Param("staff")Staff staff);
}
