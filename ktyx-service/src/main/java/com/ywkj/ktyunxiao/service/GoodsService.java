package com.ywkj.ktyunxiao.service;


import com.ywkj.ktyunxiao.model.Goods;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelGoodsPojo;
import com.ywkj.ktyunxiao.model.vo.GoodsAndImageVO;
import com.ywkj.ktyunxiao.model.vo.GoodsGroupVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品资料接口
 *
 * @author MiaoGuoZhu
 * @date 2017/12/20 0020 17:14
 */
public interface GoodsService {


    /**
     * 批量插入商品信息
     *
     * @param goods List<Goods>
     * @param versionId
     * @param staff
     * @return 大于1成功
     */
    int insertGoodsByBatch(List<Goods> goods, String versionId, Staff staff);

    /**根据组id或商品id查询商品
     * @param id
     * @return
     */
    List<GoodsAndImageVO> selectGoodsByGoodsGroupOrGoodsId(String id);

    /**查询每页的商品
     * @param companyId
     * @param firstNum
     * @param pageSize
     * @param condition
     * @return
     */
    List<Goods> selectGoodsList(String companyId, int firstNum, int pageSize, String condition);

    /**获取商品分页后的总数
     * @param companyId
     * @param condition
     * @return
     */
    Integer selectGoodsListCount(String companyId, String condition);

    /**插入Excel表数据
     * @param multipartFile
     * @param staff
     * @return
     */
    int insertGoodsList4Excel(MultipartFile multipartFile, Staff staff);

    /**导出公司商品到Excel
     * @param companyId
     * @return
     */
    List<ExcelGoodsPojo> exportGoods(String companyId);

    /**根据id查询商品
     * @param goodsId
     * @return
     */
    Goods selectById(String goodsId);

    /**修改商品
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**根据分类id查询商品组
     * @param companyId
     * @param typeId
     * @param firstNum
     * @param lastNum
     * @param staff
     * @return
     */
    List<GoodsGroupVO> selectGoodsGroupByTypeId(String companyId, String typeId, int firstNum, int lastNum, Staff staff);

    /**根据查询商品组列表，可条件搜索
     * @param companyId
     * @param condition
     * @param firstNum
     * @param lastNum
     * @param staff
     * @return
     */
    List<GoodsGroupVO> selectGoodsGroupByCompanyId(String companyId, String condition, int firstNum, int lastNum, Staff staff);


    /**查询商品名称（分页）
     * @param companyId
     * @param firstNum
     * @param lastNum
     * @return
     */
    List<Goods> selectAllGoodsName(String companyId, int firstNum, int lastNum,String search);

    /**查询某一商品
     * @param companyId
     * @param goodsId
     * @return
     */
    String selectGoodsAttrById(String companyId, String goodsId);
}
