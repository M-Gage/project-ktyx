package com.ywkj.ktyunxiao.model.vo;

import com.ywkj.ktyunxiao.model.Goods;
import com.ywkj.ktyunxiao.model.GoodsImage;
import lombok.Data;

import java.util.List;

/**带图片商品视图对象
 * @author MiaoGuoZhu
 * @date 2018/6/5 16:11
 */
@Data
public class GoodsAndImageVO extends Goods {


    /**
     * 图片列表
     */
    private List<GoodsImage> goodsImages;
 }
