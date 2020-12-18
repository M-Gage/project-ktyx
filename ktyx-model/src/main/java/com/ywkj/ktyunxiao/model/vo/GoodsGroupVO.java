package com.ywkj.ktyunxiao.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**商品组视图对象
 * @author MiaoGuoZhu
 * @date 2018/6/7 9:50
 */
@Data
public class GoodsGroupVO{
    /**
     * 属性集合
     */
    private Map<String,List<String>> standardAttributeList;
    /**
     * 除去最短属性的组商品信息
     */
    private List<GoodsAndImageVO> goodsAndImageVOList;
}
