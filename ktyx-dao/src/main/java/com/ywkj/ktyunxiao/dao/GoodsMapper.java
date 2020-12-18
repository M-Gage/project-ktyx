package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.model.Goods;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelGoodsPojo;
import com.ywkj.ktyunxiao.model.vo.GoodsAndImageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/30 17:08
 */
@Component
public interface GoodsMapper {

    /**批量插入商品
     * @param goods
     * @return
     */
    int insertByBatch(List<Goods> goods);

    /**根据组id或商品id查询商品
     * @param id
     * @param path
     * @return
     */
    List<GoodsAndImageVO> selectGoodsByGoodsGroupOrGoodsId(@Param("id") String id, @Param("path") String path);

    /**查询每页的商品
     * @param companyId
     * @param firstNum
     * @param lastNum
     * @param condition
     * @return
     */
    List<Goods> selectGoodsList(@Param("companyId") String companyId,
                                @Param("firstNum") int firstNum,
                                @Param("lastNum") int lastNum,
                                @Param("condition") String condition);

    /**获取商品分页后的总数
     * @param companyId
     * @param condition
     * @return
     */
    Integer selectGoodsListCount(@Param("companyId") String companyId,
                                 @Param("condition") String condition);

    /**查询ExcelGoodsPojo需要的数据
     * @param companyId
     * @return
     */
    List<ExcelGoodsPojo> exportGoods(String  companyId);


    /**修改商品
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**查询商品组id（根据分类id，根据查询条件）
     * @param companyId
     * @param condition 条件
     * @param firstNum
     * @param lastNum
     * @param isTypeId 是否是分类id
     * @return
     */
    List<String> selectGroupIdList(@Param("companyId") String companyId,
                                   @Param("condition")String condition,
                                   @Param("firstNum")int firstNum,
                                   @Param("lastNum")int lastNum,
                                   @Param("isTypeId")boolean isTypeId);

    /**查询商品名称（分页）
     * @param companyId
     * @param firstNum
     * @param lastNum
     * @param search
     * @return
     */
    List<Goods> selectAllGoodsName(@Param("companyId")String companyId,
                                   @Param("firstNum")int firstNum,
                                   @Param("lastNum")int lastNum,
                                   @Param("search")String search);

    /**查询商品的规格和辅助属性
     * @param companyId
     * @param goodsId
     * @return
     */
    String selectGoodsAttrById(@Param("companyId")String companyId,
                               @Param("goodsId")String goodsId);
}
