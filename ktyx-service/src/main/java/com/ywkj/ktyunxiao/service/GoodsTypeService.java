package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.model.GoodsType;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.model.vo.TypeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map; /**
 * @author MiaoGuoZhu
 * @date 2018/5/24 14:47
 */
public interface GoodsTypeService {

    int insertTypeSelective(StaffPojo staff, Map<String, Object> typeList);

    String selectTypeByTypeId(StaffPojo staff, String typeId);

    List<GoodsType> selectAllType(String companyId);

    GoodsType selectTypeByName(String typeName, String companyId);

    List<GoodsType> selectParentType(String companyId);

    int updateOrDeleteType(String typeName, String newTypeName, String companyId);

    int insertTypeList(MultipartFile multipartFile, StaffPojo staff);

    List<TypeVO> selectType4App(String companyId);

}
