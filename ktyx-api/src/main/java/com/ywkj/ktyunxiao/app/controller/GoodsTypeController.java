package com.ywkj.ktyunxiao.app.controller;

import com.ywkj.ktyunxiao.common.config.SystemConfig;
import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.vo.TypeVO;
import com.ywkj.ktyunxiao.service.GoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**商品分类控制器
 * @author MiaoGuoZhu
 * @date 2018/7/5 16:07
 */
@Slf4j
@Api(description = "商品分类")
@RestController(value = "goodsType")
@RequestMapping(value = "/api/type")
public class GoodsTypeController {

    @Autowired
    private CacheManager cacheManager;


    @ApiOperation(value = "查找商品分类")
    @GetMapping(value = "")
    public JsonResult selectType() {
        Cache cache = cacheManager.getCache(SystemConfig.cacheType);
        Map<Object,Element> elements = cache.getAll(cache.getKeys());
        if(elements!=null){
            for (Map.Entry<Object, Element> entry : elements.entrySet()) {
                List<TypeVO> types = (List<TypeVO>) entry.getValue().getObjectValue();
                return JsonResult.success(types);
            }
        }
        return JsonResult.failed();
    }
}
