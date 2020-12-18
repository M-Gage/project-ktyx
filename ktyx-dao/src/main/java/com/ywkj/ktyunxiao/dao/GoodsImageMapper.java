package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.GoodsImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/31 10:18
 */
@Component
public interface GoodsImageMapper {
    /**
     * 批量插入商品图片
     *
     * @param goodsImage
     * @return
     */
    int insertGoodsImage(@Param("goodsImage") List<GoodsImage> goodsImage);

    /**
     * 根据商品id删除图片
     *
     * @param goodsId
     * @return
     */
    int deleteGoodsImage(String goodsId);
}
