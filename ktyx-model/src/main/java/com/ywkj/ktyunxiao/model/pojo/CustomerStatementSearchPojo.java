package com.ywkj.ktyunxiao.model.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/28 10:33
 */
@Data
public class CustomerStatementSearchPojo extends StatementSearchBase {

    /**
     * 标签id列表
     */
    List<String> labelList ;

}
