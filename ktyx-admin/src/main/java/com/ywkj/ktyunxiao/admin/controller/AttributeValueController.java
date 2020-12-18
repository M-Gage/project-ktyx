package com.ywkj.ktyunxiao.admin.controller;


import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.AttributeValue;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.AttributesValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性值控制器
 *
 * @author MiaoGuoZhu
 * @date 2017/12/26 0026 17:41
 */
@RestController
@RequestMapping("/attributeValue")
public class AttributeValueController {
    @Autowired
    private AttributesValueService attributesValueService;

    /**
     * 查询商品属性的属性值
     *
     * @param staff
     * @return
     */
    @GetMapping(value = "")
    public JsonResult selectAttributeValuesByAttributeId(@SessionAttribute("staff") Staff staff, Integer attributeId) {
        List<AttributeValue> attributeValues =
                attributesValueService.selectAttributeValuesByAttributeId(staff.getCompanyId(), attributeId);
        return JsonResult.success(attributeValues);
    }

    /**
     * 添加商品属性的属性值
     *
     * @param attributeValues
     * @param attributeId
     * @param staff
     * @return
     */
    @PostMapping(value = "")
    public JsonResult insertAttributeValue(@RequestParam("attributeValue") String attributeValues,
                                           @RequestParam("attributeId") Integer attributeId,
                                           @SessionAttribute("staff") Staff staff) {
        int i = attributesValueService.insertAttributeValue(attributeValues, attributeId, staff);
        return i > 0 ? JsonResult.success(i) : JsonResult.error(Code.PARAM_ERROR);
    }
}
