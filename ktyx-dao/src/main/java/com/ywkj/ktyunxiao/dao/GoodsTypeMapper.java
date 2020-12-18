package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.GoodsType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分类映射接口
 *
 * @author MiaoGuoZhu
 * @date 2018/5/24 14:49
 */
@Component
public interface GoodsTypeMapper {

    /**
     * 插入分类
     *
     * @param type
     * @return
     */
    int insertType(GoodsType type);

    /**
     * 根据id查询子分类
     *
     * @param typeId
     * @param companyId
     * @return
     */
    List<GoodsType> selectChildType(@Param("typeId") String typeId, @Param("companyId") String companyId);

    /**
     * 查询所有父类
     *
     * @param companyId
     * @return
     */
    List<GoodsType> selectParentType(@Param("companyId")String companyId);

    /**
     * 查询全部分类
     *
     * @param companyId
     * @return
     */
    List<GoodsType> selectAllType(@Param("companyId")String companyId);

    /**
     * 根据类型名称查询类别
     *
     * @param typeName
     * @param companyId
     * @return
     */
    GoodsType selectTypeByName(@Param("typeName")String typeName, @Param("companyId")String companyId);

    /**
     * 根据类型id查询类型
     *
     * @param companyId
     * @param typeId
     * @return
     */
    String selectTypeByTypeId(@Param("companyId") String companyId,@Param("typeId") String typeId);

    /**
     * 根据名称删除分类
     *
     * @param typeName
     * @param companyId
     * @return
     */
    int deleteType(@Param("typeName")String typeName,@Param("companyId")String companyId);

    /**
     * 根据名称修改分类名称
     *
     * @param typeName
     * @param newTypeName
     * @param companyId
     * @return
     */
    int updateType(@Param("typeName")String typeName, @Param("newTypeName")String newTypeName, @Param("companyId")String companyId);


    /**
     * 批量插入分类
     * @param goodsTypes    分类集合
     * @return
     */
    int insertTypeList(@Param("goodsTypes") List<GoodsType> goodsTypes);
}
