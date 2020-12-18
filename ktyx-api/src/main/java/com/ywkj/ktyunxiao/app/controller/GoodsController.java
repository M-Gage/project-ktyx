package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.vo.GoodsGroupVO;
import com.ywkj.ktyunxiao.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/6 16:09
 */
@Slf4j
@Api(description = "商品资料")
@RestController(value = "apiGoods")
@RequestMapping(value = "/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "按组类型分页查询商品")
    @GetMapping(value = "/type")
    public JsonResult selectGoodsByTypeId(int pageNumber,
                                          int pageSize,
                                          String typeId,
                                          @ApiIgnore @RequestAttribute("staff") Staff staff) {
        List<GoodsGroupVO> group = goodsService.selectGoodsGroupByTypeId(staff.getCompanyId(), typeId, (pageNumber - 1) * pageSize, pageSize, staff);
        return JsonResult.success(group);
    }



    @ApiOperation(value = "按组分页查询组商品(可条件查询)")
    @GetMapping(value = "/group")
    public JsonResult selectGoods4GroupByCompanyId(int pageNumber,
                                                   int pageSize,
                                                   @RequestParam(value = "condition", required = false) String condition,
                                                   @ApiIgnore @RequestAttribute("staff") Staff staff) {

        int firstNum = (pageNumber - 1) * pageSize;
        List<GoodsGroupVO> group = goodsService.selectGoodsGroupByCompanyId(staff.getCompanyId(), condition, firstNum, pageSize, staff);
        return JsonResult.success(group);
    }
}
