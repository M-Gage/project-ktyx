package com.ywkj.ktyunxiao.service;

import com.ywkj.ktyunxiao.common.utils.JsonResult;
import com.ywkj.ktyunxiao.model.RoutePlan;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.RoutePlanPojo;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/9 13:37
 */
public interface RoutePlanService {
    /**添加路线规划
     * @param routePlanPojos
     * @param routePlanDate
     * @param staff
     * @return
     */
    int insertRouteList(List<RoutePlanPojo> routePlanPojos, String routePlanDate, Staff staff);

    /**根据选择的职员获取所有的规划
     * @param staffId
     * @param companyId
     * @return
     */
    List<RoutePlan> selectAllRoutePlanByStaffId(String staffId, String companyId);

    /**完成规划
     * @param companyId
     * @param staffId
     * @param routePlanId
     * @return
     */
    int updateRoutePlanState(String companyId, String staffId, Integer routePlanId);

    /**根据日程ID获取规划详情
     * @param companyId
     * @param routePlanId
     * @return
     */
    RoutePlan selectRoutePlanByRoutePlanId(String companyId, Integer routePlanId);

    /**根据日期查询路线
     * @param staffId
     * @param date
     * @return
     */
    List<RoutePlan> selectRoutePlanByDate(String staffId, String date);
}
