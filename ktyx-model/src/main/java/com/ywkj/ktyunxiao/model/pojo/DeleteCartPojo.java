package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/7/5 12:05
 */
@Data
public class DeleteCartPojo {

    private String customerId;

    private List<String> goodsIds;
}
