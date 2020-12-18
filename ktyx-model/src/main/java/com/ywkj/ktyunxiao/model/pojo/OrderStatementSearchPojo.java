package com.ywkj.ktyunxiao.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import lombok.Data;

/**报表查询对象
 * @author MiaoGuoZhu
 * @date 2018/6/14 11:30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatementSearchPojo extends StatementSearchBase {



    /**
     *是否 APP
     */
    private boolean isApp;

    /**
     *是否团队贡献
     */
    private Boolean isTeamContribute;
    /**
     *客户贡献
     */
    private Boolean isCustomerContribute;
    /**
     *团队排行
     */
    private Boolean isTeamRank;

    /**
     *区域名称
     */
    private String area;

    /**
     * 是否省名
     */
    private Boolean isProvince;


}
