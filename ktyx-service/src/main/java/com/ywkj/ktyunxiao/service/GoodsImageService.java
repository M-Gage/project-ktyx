package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.GoodsImage;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/31 9:26
 */
public interface GoodsImageService {
    /**根据商品id删除商品图片
     * @param GoodsId
     * @return
     */
    int deleteGoodsImage(String GoodsId);

    /**插入商品图片
     * @param goodsImage
     * @return
     */
    int insertGoodsImage(List<GoodsImage> goodsImage);
}
