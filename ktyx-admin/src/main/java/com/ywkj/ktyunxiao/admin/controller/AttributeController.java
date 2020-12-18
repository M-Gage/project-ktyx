package com.ywkj.ktyunxiao.admin.controller;

import com.ywkj.ktyunxiao.common.enums.Code;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Attribute;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品属性控制器
 *
 * @author MiaoGuoZhu
 * @date 2017/12/26 0026 10:43
 */
@RestController
@RequestMapping("/attribute")
public class AttributeController {


    @Autowired
    private AttributeService attributeService;

    /**
     * 添加商品属性
     *
     * @param goodsAttributeName
     * @param isNonstandard
     * @param staff
     * @return
     */
    @PostMapping(value = "")
    public JsonResult insertGoodsAttribute(@RequestParam(value = "goodsAttribute") String goodsAttributeName,
                                           @RequestParam(value = "isNonstandard", required = false,defaultValue = "0") Integer isNonstandard,
                                           @SessionAttribute("staff") Staff staff) {
        if (StringUtil.isNotEmpty(goodsAttributeName)) {
            Attribute attribute = new Attribute(goodsAttributeName, isNonstandard, staff.getCompanyId());
            attribute = attributeService.insertAttribute(attribute);
            return attribute != null ? JsonResult.success(attribute.getAttributeId()) : JsonResult.failed();
        }
        return JsonResult.error(Code.PARAM_ERROR);
    }

    /**
     * 查询商品属性
     *
     * @param isNonstandard
     * @param staff
     * @return
     */
    @GetMapping(value = "")
    public JsonResult selectAllGoodsAttribute(@RequestParam(value = "isNonstandard", required = false,defaultValue = "0") Integer isNonstandard,
                                              @SessionAttribute("staff") Staff staff) {
        List<Attribute> goodsAttributes = attributeService.selectAllGoodAttribute(isNonstandard, staff.getCompanyId());
        return (goodsAttributes != null && goodsAttributes.size() > 0) ? JsonResult.success(goodsAttributes) : JsonResult.error(Code.IS_NULL_DATA);
    }
}
