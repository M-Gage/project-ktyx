package com.ywkj.ktyunxiao.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**app商品分类
 * @author MiaoGuoZhu
 * @date 2018/7/5 16:14
 */
@Data
public class TypeVO {
    @ApiModelProperty(value = "分类主键")
    private String typeId;
    @ApiModelProperty(value = "分类名称")
    private String typeName;
    @ApiModelProperty(value = "分类集合")
    List<TypeVO> types;
}
