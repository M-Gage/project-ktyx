package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.dao.GoodsImageMapper;
import com.ywkj.ktyunxiao.dao.GoodsMapper;
import com.ywkj.ktyunxiao.model.GoodsImage;
import com.ywkj.ktyunxiao.service.GoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/31 10:11
 */
@Service
public class GoodsImageServiceImpl implements GoodsImageService {
    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Override
    public int deleteGoodsImage(String goodsId) {
        return goodsImageMapper.deleteGoodsImage(goodsId);
    }

    @Override
    public int insertGoodsImage(List<GoodsImage> goodsImage) {
        return goodsImageMapper.insertGoodsImage(goodsImage);
    }
}
