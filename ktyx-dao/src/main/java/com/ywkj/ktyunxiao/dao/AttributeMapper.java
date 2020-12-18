package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Attribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/28 16:50
 */
@Component
public interface AttributeMapper {

    /**
     * 插入属性（可适应）
     * @param attribute 属性对象
     * @return >0成功
     */
    int insertAttribute(Attribute attribute);

   /**
     * 根据缺值对象查询属性
     * @param attribute 属性对象
     * @return GoodsAttributes
     */
//    List<Attribute> selectByPrimaryKey(Attribute attribute);


    /**
     * 查询全部商品属性
     * @return List<GoodsAttributes>
     * @param isNonstandard
     * @param companyId
     */
    List<Attribute> selectAll(@Param(value = "isNonstandard") Integer isNonstandard, @Param(value = "companyId") String companyId);

}
