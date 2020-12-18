package com.ywkj.ktyunxiao.service.impl;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.utils.ExcelUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.dao.GoodsTypeMapper;
import com.ywkj.ktyunxiao.model.GoodsType;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.vo.TypeVO;
import com.ywkj.ktyunxiao.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.Cacheable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/24 14:47
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {




    @Autowired
    private GoodsTypeMapper goodsTypeMapper;


    private void insertType(GoodsType type) {
        goodsTypeMapper.insertType(type);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertTypeSelective(StaffPojo staff, Map<String, Object> typeList) {

        //插入前添加是否父类目，父类目ID，等级，公司
        int i = 0;
        if (typeList.size() > 0 && typeList.get("typeName") != null) {
            List<String> typeName = (ArrayList<String>) typeList.get("typeName");
            String typeId = (String) typeList.get("typeId");

            List<GoodsType> goodsTypes = selectParentType(staff.getCompanyId());
            /*没有这个分类 那么这一组分类全部新建*/
            if (typeId == null) {

                /* 第一级分类 */
                String parentId = String.valueOf(SystemConfig.INIT_TYPE_ID + goodsTypes.size());
                setType(staff, typeName, parentId, "0", 1, 1);
                i++;

                /*判断有第二级 加在一级下面*/
                if (!"".equals(typeName.get(1))) {
                    setType(staff,
                            typeName,
                            parentId + "01",
                            parentId + "",
                            2, 1);
                    i++;
                }

                /*判断有第三级 加在二级下面 只有三级*/
                if (!"".equals(typeName.get(2))) {
                    setType(staff, typeName,
                            parentId + "0101",
                            parentId + "01",
                            3, 0);
                    i++;
                }

                /*带父类*/
            } else {

                /*发送的是最后一个分类id*/
                /*有一级分类*/
                if (typeId.length() == 4) {
                    List<GoodsType> childType = selectChildType(typeId, staff.getCompanyId());
                    String secondTypeId = typeId + StringUtil.getIdCard(childType.size() + 1, 2);
                    /*添加二级分类*/
                    setType(staff,
                            typeName,
                            secondTypeId,
                            typeId, 2, 1);
                    i++;

                    /*添加三级分类*/
                    if (!"".equals(typeName.get(2))) {
                        setType(staff, typeName,
                                secondTypeId + "01",
                                secondTypeId, 3, 0);
                        i++;
                    }
                    /*有二级分类*/
                } else {
                    List<GoodsType> childType = selectChildType(typeId, staff.getCompanyId());
                    setType(staff, typeName,
                            typeId + StringUtil.getIdCard(childType.size() + 1, 2),
                            typeId, 3, 0);
                    i++;
                }
            }
        }
        return i;

    }

    @Override
    public String selectTypeByTypeId(StaffPojo staff, String typeId) {
        return goodsTypeMapper.selectTypeByTypeId(staff.getCompanyId(), typeId);
    }


    @Override
    public List<GoodsType> selectAllType(String companyId) {
        return goodsTypeMapper.selectAllType(companyId);
    }

    @Override
    public GoodsType selectTypeByName(String typeName, String companyId) {
        return goodsTypeMapper.selectTypeByName(typeName, companyId);
    }

    @Override
    public List<GoodsType> selectParentType(String companyId) {
        return goodsTypeMapper.selectParentType(companyId);
    }

    @Override
    public int updateOrDeleteType(String typeName, String newTypeName, String companyId) {
        int i;
        try {
            i = !StringUtil.isNotEmpty(newTypeName) ? goodsTypeMapper.deleteType(typeName, companyId) : goodsTypeMapper.updateType(typeName, newTypeName, companyId);
        } catch (Exception e) {
            i = 0;
            e.printStackTrace();
        }
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertTypeList(MultipartFile multipartFile, StaffPojo staff) {
        String oldFileName = multipartFile.getOriginalFilename();
        int typeCount = 0;
        List<List<String>> excelData = null;
        List<String> typeDtosLv1 = new LinkedList<>();
        List<String> typeDtosLv1Code = new LinkedList<>();
        List<String> typeDtosLv2 = new LinkedList<>();
        List<String> typeDtosLv2Code = new LinkedList<>();
        List<String> typeDtosLv3 = new LinkedList<>();
        List<String> typeDtosLv3Code = new LinkedList<>();
        List<GoodsType> goodsTypes = new ArrayList<>();
        try {
            excelData = ExcelUtil.getExcelData(multipartFile.getInputStream(), oldFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (excelData != null) {
            for (int i = 1; i < excelData.size(); i++) {
                if (!typeDtosLv1.contains(excelData.get(i).get(0))) {
                    typeDtosLv1.add(excelData.get(i).get(0));
                    typeDtosLv1Code.add("1" + StringUtil.getIdCard(typeDtosLv1.size(), 3));
                }
                if (excelData.get(i).size() > 1 && !typeDtosLv2.contains(excelData.get(i).get(1)) && !excelData.get(i).get(1).equals("")) {
                    typeDtosLv2.add(excelData.get(i).get(1));
                    int count = (typeDtosLv2Code.size() + 1);
                    typeDtosLv2Code.add(typeDtosLv1Code.get(typeDtosLv1Code.size() - 1) + (count < 9 ? "0" + count : count));
                }
                if (excelData.get(i).size() > 2 && !typeDtosLv3.contains(excelData.get(i).get(2)) && !excelData.get(i).get(2).equals("")) {
                    typeDtosLv3.add(excelData.get(i).get(2));
                    typeDtosLv3Code.add(typeDtosLv2Code.get(typeDtosLv2Code.size() - 1) + (typeDtosLv3Code.size() + 1));
                }
            }
            for (int i = 0; i < typeDtosLv1.size(); i++) {
                GoodsType goodsType = new GoodsType(
                        staff.getCompanyId(),
                        typeDtosLv1Code.get(i),
                        "0",
                        typeDtosLv1.get(i),
                        1,
                        1
                );
                goodsTypes.add(goodsType);
            }
            for (int i = 0; i < typeDtosLv2.size(); i++) {
                GoodsType goodsType = new GoodsType(
                        staff.getCompanyId(),
                        typeDtosLv2Code.get(i),
                        typeDtosLv2Code.get(i).substring(0, typeDtosLv2Code.get(i).length() - 2),
                        typeDtosLv2.get(i),
                        1,
                        2
                );
                goodsTypes.add(goodsType);
            }
            for (int i = 0; i < typeDtosLv3.size(); i++) {
                GoodsType goodsType = new GoodsType(
                        staff.getCompanyId(),
                        typeDtosLv3Code.get(i),
                        typeDtosLv3Code.get(i).substring(0, typeDtosLv3Code.get(i).length() - 2),
                        typeDtosLv3.get(i),
                        0,
                        3
                );
                goodsTypes.add(goodsType);
            }
            typeCount += goodsTypeMapper.insertTypeList(goodsTypes);
        }
        return typeCount;
    }


    @Override
    @CachePut(value = "typeCache",key="#companyId")
    public List<TypeVO> selectType4App(String companyId) {
        List<GoodsType> goodsTypes = selectParentType(companyId);
        return getTypePojos(goodsTypes,companyId);
    }

    public List<GoodsType> selectChildType(String typeId, String companyId) {
        return goodsTypeMapper.selectChildType(typeId, companyId);
    }


    private void setType(StaffPojo staff, List<String> typeName, String typeId, String parentId, int rank, int isParent) {
        GoodsType type = new GoodsType();
        type.setCompanyId(staff.getCompanyId());
        type.setIsParent(isParent);
        type.setRank(rank);
        type.setPreTypeId(parentId);
        type.setTypeId(typeId);
        type.setTypeName(typeName.get(rank - 1));
        insertType(type);
    }

    private List<TypeVO> getTypePojos(List<GoodsType> goodsTypes, String companyId) {
        List<TypeVO> types = null;
        if (goodsTypes != null && goodsTypes.size() > 0) {
            types = new ArrayList<>();
            for (GoodsType goodsType : goodsTypes) {
                TypeVO typePojo = new TypeVO();
                typePojo.setTypeId(String.valueOf(goodsType.getTypeId()));
                typePojo.setTypeName(goodsType.getTypeName());
                List<GoodsType> childType = selectChildType(goodsType.getTypeId(), companyId);
                typePojo.setTypes(getTypePojos(childType, companyId));
                types.add(typePojo);
            }
        }
        return types;
    }

}
