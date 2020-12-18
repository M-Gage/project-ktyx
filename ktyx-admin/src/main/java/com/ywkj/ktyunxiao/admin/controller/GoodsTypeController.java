package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.GoodsType;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import com.ywkj.ktyunxiao.service.impl.GoodsTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/24 14:42
 */
@RestController
@RequestMapping("type")
public class GoodsTypeController {

    @Autowired
    private GoodsTypeServiceImpl goodsTypeService;

    /**
     * 添加商品分类
     *
     * @param typeList
     * @param session
     * @return
     */
    @PostMapping(value = "")
    public JsonResult insertType(@RequestBody Map<String, Object> typeList, HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        int i = goodsTypeService.insertTypeSelective(staff, typeList);
        //是否插入成功
        return JsonResult.success(i > 0 ? Code.SUCCESS : Code.ERROR);
    }

    /**
     * 查询商品分类
     *
     * @param typeId
     * @param session
     * @return
     */
    @GetMapping(value = "/get/{typeId}")
    public JsonResult selectTypeByTypeId(@PathVariable(value = "typeId") String typeId, HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        String typeName = goodsTypeService.selectTypeByTypeId(staff, typeId);
        return !"".equals(typeName)?JsonResult.success( typeName):JsonResult.failed();
    }


    /**
     *查找商品分类
     *
     * @param session
     * @return
     */
    @GetMapping(value = "")
    public JsonResult selectType(HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        List<GoodsType> goodsTypes = goodsTypeService.selectAllType(staff.getCompanyId());
        return goodsTypes != null && goodsTypes.size() > 0 ? JsonResult.success(goodsTypes):JsonResult.failed();

    }

    /**
     * 校验分类名称
     *
     * @param typeName
     * @param session
     * @return
     */
    @PostMapping(value = "/check/{typeName}")
    public JsonResult checkTypeName(@PathVariable String typeName, HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        GoodsType goodsType = goodsTypeService.selectTypeByName(typeName, staff.getCompanyId());
        return goodsType != null ? JsonResult.failed() : JsonResult.success();
    }

    /**
     * 查找分类一级类目
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/parentType")
    public JsonResult selectParentTypes(HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        List<GoodsType> goodsTypes = goodsTypeService.selectParentType(staff.getCompanyId());
        return (goodsTypes == null || goodsTypes.size() == 0) ? JsonResult.error(Code.IS_NULL_DATA)  : JsonResult.success(goodsTypes) ;
    }

    /**
     * 查找分类子类目
     *
     * @param typeId
     * @param session
     * @return
     */
    @GetMapping(value = "/{typeId}")
    public JsonResult selectChildTypes(@PathVariable String typeId, HttpSession session) {
        Staff staff = (Staff) session.getAttribute("staff");
        if (typeId != null) {
            List<GoodsType> parentType = goodsTypeService.selectChildType(typeId, staff.getCompanyId());
            return parentType != null && parentType.size() != 0 ? JsonResult.success(parentType): JsonResult.failed();
        } else {
            return JsonResult.failed();
        }
    }

    @PostMapping(value = "/uploadExcel")
    public JsonResult uploadExcel(@RequestParam("file")MultipartFile multipartFile, HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        int i = goodsTypeService.insertTypeList(multipartFile, staff);
        return JsonResult.ok("成功导入类型："+i+"条数据");
    }

    /**
     * 修改或删除类型
     *
     * @param typeName
     * @param newTypeName
     * @param session
     * @return
     */
    @PutMapping(value = "")
    public JsonResult updateOrDeleteType(@RequestParam("typeName")String typeName,
                                                  @RequestParam(value = "newTypeName",required = false) String newTypeName ,
                                                  HttpSession session) {
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");
        return JsonResult.success(goodsTypeService.updateOrDeleteType(typeName, newTypeName, staff.getCompanyId()));
    }


}
