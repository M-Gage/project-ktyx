package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.exception.CheckException;
import com.ywkj.ktyunxiao.common.utils.DateUtil;
import com.ywkj.ktyunxiao.common.utils.ExcelUtil;
import com.ywkj.ktyunxiao.common.utils.PinYinUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.GoodsMapper;
import com.ywkj.ktyunxiao.dao.GoodsTypeMapper;
import com.ywkj.ktyunxiao.model.Goods;
import com.ywkj.ktyunxiao.model.GoodsType;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.excel.ExcelGoodsPojo;
import com.ywkj.ktyunxiao.model.vo.GoodsAndImageVO;
import com.ywkj.ktyunxiao.model.vo.GoodsGroupVO;
import com.ywkj.ktyunxiao.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author MiaoGuoZhu
 * @date 2017/12/20 0020 17:25
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public int insertGoodsByBatch(List<Goods> goods, String versionId, Staff staff) {
        goods.forEach(g -> {
            g.setGoodsId(StringUtil.getUUID());
            g.setCompanyId(staff.getCompanyId());
            g.setHelpCode(PinYinUtil.getPinYinHeadChar(g.getGoodsName()));
            g.setGroupId(versionId);
        });
        return goodsMapper.insertByBatch(goods);
    }

    @Override
    public List<GoodsAndImageVO> selectGoodsByGoodsGroupOrGoodsId(String id) {
        return goodsMapper.selectGoodsByGoodsGroupOrGoodsId(id, SystemConfig.resourceName);
    }

    @Override
    public List<Goods> selectGoodsList(String companyId, int firstNum, int lastNum, String condition) {
        return goodsMapper.selectGoodsList(companyId, firstNum, lastNum, condition);
    }


    @Override
    public Integer selectGoodsListCount(String companyId, String condition) {
        return goodsMapper.selectGoodsListCount(companyId, condition);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertGoodsList4Excel(MultipartFile multipartFile, Staff staff) {
        int goodCount = 0;
        String oldFileName = multipartFile.getOriginalFilename();
        List<Goods> goodsList = new ArrayList<>();
        List<List<String>> lists = null;
        List<String> typeNameList = new ArrayList<>(3);
        List<GoodsType> typeList = goodsTypeMapper.selectAllType(staff.getCompanyId());
        try {
            lists = ExcelUtil.getExcelData(multipartFile.getInputStream(), oldFileName);
        } catch (IOException e) {
            log.error("Excel 转换失败", e);
        }
        if (lists != null) {
            for (int i = 1; i < lists.size(); i++) {
                List<String> objectList = lists.get(i);
                if (objectList.size() < 15) {
                    throw new CheckException("第：" + i + "行，数据不完整,请正确填写完整！");
                } else {
                    if (i == 1) {
                        for (int j = 15; j <= lists.get(i).size() - 1; j++) {
                            typeNameList.add(lists.get(i).get(j));
                        }
                    } else {
                        //辅助属性
                        int temp = 0;
                        StringBuilder assists = new StringBuilder();
                        for (int j = 15; j <= lists.get(i).size() - 1; j++) {
                            temp++;
                            assists.append(typeNameList.get(temp - 1)).append("：").append(lists.get(i).get(j)).append(" ");
                        }
                        String typeId = "";
                        //类型
                        for (GoodsType goodsType : typeList) {
                            if (goodsType.getTypeName().equals(objectList.get(12))) {
                                typeId = String.valueOf(goodsType.getTypeId());
                            }
                        }
                        Goods goods = null;
                        try {

                            goods = new Goods(
                                    StringUtil.getUUID(),
                                    objectList.get(1),
                                    objectList.get(2),
                                    StringUtil.getUUID(),
                                    staff.getCompanyId(),
                                    objectList.get(0),
                                    new BigDecimal(objectList.get(6)),
                                    new BigDecimal(objectList.get(5)),
                                    objectList.get(4),
                                    typeId,
                                    Integer.parseInt("".equals(objectList.get(10)) ? "0" : objectList.get(10)),
                                    Integer.parseInt("".equals(objectList.get(11)) ? "0" : objectList.get(11)),
                                    PinYinUtil.getPinYinHeadChar(objectList.get(1)),
                                    objectList.get(9),
                                    objectList.get(7),
                                    DateUtil.format(DateUtil.dateToNumberStr(objectList.get(8))),
                                    new Date(),
                                    objectList.get(3),
                                    objectList.get(12),
                                    objectList.get(13),
                                    objectList.get(14),
                                    assists.toString()
                            );
                        } catch (NumberFormatException e) {
                            throw new CheckException("第：" + i + "条有错误");
                        }
                        goodsList.add(goods);
                        if (goodsList.size() == 100) {
                            goodCount += goodsMapper.insertByBatch(goodsList);
                            goodsList.clear();
                        }
                    }
                }
            }
            if (goodsList.size() > 0) {
                goodCount += goodsMapper.insertByBatch(goodsList);
            }
        }
        return goodCount;
    }

    @Override
    public List<ExcelGoodsPojo> exportGoods(String companyId) {
        return goodsMapper.exportGoods(companyId);
    }

    @Override
    public Goods selectById(String goodsId) {
        return goodsMapper.selectGoodsByGoodsGroupOrGoodsId(goodsId, SystemConfig.resourceName).get(0);
    }

    @Override
    public int updateGoods(Goods goods) {
        return goodsMapper.updateGoods(goods);
    }

    @Override
    public List<GoodsGroupVO> selectGoodsGroupByTypeId(String companyId, String typeId, int firstNum, int lastNum, Staff staff) {
        List<String> groupIdList = goodsMapper.selectGroupIdList(companyId, typeId, firstNum, lastNum, true);
        if (groupIdList.size() == 0) {
            return null;
        }
        return getGroup(companyId, groupIdList, staff);
//        return null;
    }


    @Override
    public List<GoodsGroupVO> selectGoodsGroupByCompanyId(String companyId, String condition, int firstNum, int lastNum, Staff staff) {
        //获取全部组id
        List<String> groupIdList = goodsMapper.selectGroupIdList(companyId, condition, firstNum, lastNum, false);
        if (groupIdList.size() == 0) {
            return null;
        }
        return getGroup(companyId, groupIdList, staff);
//        return null;
    }

    @Override
    public List<Goods> selectAllGoodsName(String companyId, int firstNum, int lastNum, String search) {
        return goodsMapper.selectAllGoodsName(companyId, firstNum, lastNum, search);
    }

    @Override
    public String selectGoodsAttrById(String companyId, String goodsId) {
        return goodsMapper.selectGoodsAttrById(companyId, goodsId);
    }


    private List<GoodsGroupVO> getGroup(String companyId, List<String> groupIdList, Staff staff) {

        List<GoodsGroupVO> groupList = new ArrayList<>();

        for (String integer : groupIdList) {
            GoodsGroupVO groupVO = new GoodsGroupVO();

            //获取每个组的商品
            List<GoodsAndImageVO> goodsList = selectGoodsByGoodsGroupOrGoodsId(integer);
            groupVO.setGoodsAndImageVOList(goodsList);

            //只有一个商品那么过
            if (goodsList.size() == 1) {
                groupList.add(groupVO);
                continue;
            }

            //todo 存在双数据那么库存和价格以其他库为准

            HashSet<String> stringHashMap = new HashSet<>(16);

            //遍历每一个商品
            for (Goods gs : goodsList) {
                String props = gs.getStandardAttribute();
                String[] stringss = props.split(" ");
                //去重
                for (String string : stringss) {
                    stringHashMap.add(string);
                }
            }
            Map<String, List<String>> map = new HashMap<>();

            stringHashMap.forEach(s -> {
                String[] split = s.split(":");
                String attr = split[0];
                if (map.containsKey(attr)) {
                    List<String> ls = map.get(attr);
                    ls.add(split[1]);
                    map.put(attr, ls);
                } else {
                    List<String> stringList = new ArrayList<>();
                    stringList.add(split[1]);
                    map.put(attr, stringList);
                }
            });
            groupVO.setStandardAttributeList(map);
            groupList.add(groupVO);
        }
        return groupList;
    }

}
