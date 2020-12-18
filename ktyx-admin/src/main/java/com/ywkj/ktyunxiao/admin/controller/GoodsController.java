package com.ywkj.ktyunxiao.admin.controller;


import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.FileUtil;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Goods;
import com.ywkj.ktyunxiao.model.GoodsImage;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.vo.GoodsAndImageVO;
import com.ywkj.ktyunxiao.service.GoodsImageService;
import com.ywkj.ktyunxiao.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 商品资料控制器
 *
 * @author MiaoGuoZhu
 * @date 2017/12/20 0020 16:09
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsImageService goodsImageService;


    /*商品录入*/

    /**
     * 添加无属性商品信息
     *
     * @param goods
     * @param staff
     * @return
     */
    @PostMapping(value = "")
    public JsonResult insertGoods(@RequestBody() Goods goods, @SessionAttribute("staff") Staff staff) {
        if (goods != null) {
            //使用uuid 来分开商品组
            String versionId = StringUtil.getUUID();
            List<Goods> goodsList = new ArrayList<>();
            goodsList.add(goods);
            int i = goodsService.insertGoodsByBatch(goodsList, versionId, staff);
            return i > 0 ? JsonResult.success(versionId) : JsonResult.error(Code.ERROR);
        } else {
            return JsonResult.error(Code.PARAM_ERROR);
        }
    }


    /**
     * 根据id查询商品
     *
     * @param goodsId
     * @return
     */
    @GetMapping(value = "/info/{goodsId}")
    public JsonResult selectGoodsById(@PathVariable("goodsId") String goodsId) {
        Goods goods = goodsService.selectById(goodsId);
        return goods != null ? JsonResult.success(goods) : JsonResult.failed();
    }

    /**
     * 添加有属性商品信息
     *
     * @param goodsInfo
     * @param staff
     * @return
     */
    @PostMapping(value = "/attr")
    public JsonResult insertGoods(@RequestBody() List<Goods> goodsInfo, @SessionAttribute("staff") Staff staff) {
        if (goodsInfo != null && goodsInfo.size() > 0) {
            //使用uuid 来分开商品组
            String versionId = StringUtil.getUUID();
            //批量插入
            int i = goodsService.insertGoodsByBatch(goodsInfo, versionId, staff);
            return i > 0 ? JsonResult.success(versionId) : JsonResult.error(Code.ERROR);
        } else {
            return JsonResult.error(Code.PARAM_ERROR);
        }
    }


    /**
     * 批量上传商品图片
     *
     * @param id
     * @param goodsFile
     * @return
     */
    @PostMapping(value = "/image")
    public JsonResult imageUpload(String id, @RequestParam(value = "goods_file") MultipartFile[] goodsFile) {
        goodsImageService.deleteGoodsImage(id);
        try {
            if (goodsFile != null && goodsFile.length > 0) {
                List<GoodsAndImageVO> goods = goodsService.selectGoodsByGoodsGroupOrGoodsId(id);
                List<GoodsImage> images = new ArrayList<>();
                /*图片上传到本地*/
                List<Map<String, Object>> maps = FileUtil.batchFileUpload(goodsFile);
                /*数据库录入图片地址*/
                for (Goods g : goods) {
                    maps.forEach(s -> images.add(new GoodsImage(g.getGoodsId(), s.get("newFileName").toString())));
                }
                return goodsImageService.insertGoodsImage(images) > 0 ? JsonResult.success() : JsonResult.failed();
            } else {
                return JsonResult.error(Code.PARAM_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error(Code.INSERT_ERROR);
        }
    }


    /**
     * 分页查询商品清单
     *
     * @param pageNumber
     * @param pageSize
     * @param condition
     * @param staff
     * @return
     */
    @GetMapping(value = "")
    public JsonResult selectGoodsList(int pageNumber,
                                      int pageSize,
                                      @RequestParam(value = "condition", required = false) String condition,
                                      @SessionAttribute("staff") Staff staff) {

        int firstNum = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<>(SystemConfig.MAP_INIT_SIZE);
        /*每页内容*/
        map.put("rows", goodsService.selectGoodsList(staff.getCompanyId(), firstNum, pageSize, condition));
        /*总页数*/
        map.put("total", goodsService.selectGoodsListCount(staff.getCompanyId(), condition));
        return JsonResult.success(map);
    }


    /**
     * 商品EXCEL导入
     *
     * @param multipartFile
     * @param staff
     * @return
     */
    @PostMapping(value = "/uploadExcel", headers = "content-type=multipart/form-data")
    public JsonResult uploadExcel(@RequestParam("file") MultipartFile multipartFile, @SessionAttribute("staff") Staff staff) {
        int count = goodsService.insertGoodsList4Excel(multipartFile, staff);
        return JsonResult.ok("成功导入商品：" + count + "条");
    }


    /**
     * 修改商品信息
     *
     * @param goods
     * @param goods
     * @return
     */
    @PostMapping(value = "/update")
    public JsonResult updateGoods(@RequestBody() Goods goods) {
        if (goods != null) {
            goods.setLastModifyTime(new Date());
            goods.setHelpCode(PinYinUtil.getPinYinHeadChar(goods.getGoodsName()));
            int i = goodsService.updateGoods(goods);
            return i > 0 ? JsonResult.success(goods.getGoodsId()) : JsonResult.failed();
        } else {
            return JsonResult.error(Code.PARAM_ERROR);
        }
    }


    /**
     * 查询全部商品名称
     *
     * @param staff
     * @return
     */
    @GetMapping(value = "/goodsName")
    public JsonResult selectAllGoodsName(@SessionAttribute("staff") Staff staff
            , int firstNum, int lastNum
            , @RequestParam(value = "search", required = false) String search) {
        return JsonResult.success(goodsService.selectAllGoodsName(staff.getCompanyId(), firstNum, lastNum, search));
    }


    /**
     * 根据id查询商品属性
     *
     * @param goodsId
     * @param staff
     * @return
     */
    @GetMapping(value = "/attr/{goodsId}")
    public JsonResult selectGoodsAttrById(@PathVariable("goodsId") String goodsId,
                                          @SessionAttribute("staff") Staff staff) {
        String goodsAttr = goodsService.selectGoodsAttrById(staff.getCompanyId(), goodsId);
        return (goodsAttr != null && !"".equals(goodsAttr)) ? JsonResult.success(goodsAttr) : JsonResult.error(Code.IS_NULL_DATA);
    }

/*



    @ApiOperation(value = "按组类型分页查询商品")
    @GetMapping(value = "/type")
    public JsonResult<List<GroupPojo>> selectGoodsByTypeId(int pageNumber,
                                                           int pageSize,
                                                           String typeId,
                                                           HttpSession session) {
        Staff staff = (Staff) session.getAttribute("user");
        if (staff == null) {
            return new JsonResult<>(JsonResult.ERROR, "没有员工信息，登录可能失效");
        }
        int firstNum = (pageNumber - 1) * pageSize;
        List<GoodsType> goodsTypes = goodsTypeService.selectChildType(Integer.parseInt(typeId), staff.getCompanyId());
        if (goodsTypes.size() == 0) {
            GoodsType gs = new GoodsType();
            gs.setTypeId(Long.parseLong(typeId));
            goodsTypes.add(gs);
        }
        List<GroupPojo> groupPojos = goodsService.selectGoodsByTypeId(staff.getCompanyId(), goodsTypes, firstNum, pageSize, staff);
        return  new JsonResult<>(JsonResult.SUCCESS, groupPojos);
    }

    @ApiOperation(value = "按组分页查询组商品(可条件查询)")
    @GetMapping(value = "/group")
    public JsonResult<List<GroupPojo>> selectGoods4GroupByCompanyId(int pageNumber,
                                                                    int pageSize,
                                                                    @RequestParam(value = "condition", required = false) String condition,
                                                                    HttpSession session) {
        Staff staff = (Staff) session.getAttribute("user");
        if (staff == null) {
            return new JsonResult<>(JsonResult.ERROR, "没有员工信息，登录可能失效");
        }
        int firstNum = (pageNumber - 1) * pageSize;
        List<GroupPojo> groupPojos = goodsService.selectGoods4GroupByCompanyId(staff.getCompanyId(), condition, firstNum, pageSize, staff);
        return new JsonResult<>(JsonResult.SUCCESS, groupPojos);
    }





*/


}
