package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.Attribute;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/28 16:30
 */
public interface AttributeService {

    /**
     * 添加商品属性
     *
     * @param attribute 属性名
     * @return >0成功
     */
    Attribute insertAttribute(Attribute attribute);

    /**
     * 查询全部商品属性
     * @return 商品属性集合
     * @param isNonstandard
     * @param companyId
     */
    List<Attribute> selectAllGoodAttribute(Integer isNonstandard, String companyId);

    /**
     *
     * 查询商品属性(可适应)
     * @param attribute 属性对象（缺值）
     * @return 商品属性（全）
     */
    List<Attribute>  selectGoodAttribute(Attribute attribute);
}
