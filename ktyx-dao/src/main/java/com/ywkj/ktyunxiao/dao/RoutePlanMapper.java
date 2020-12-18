package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.RoutePlan;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.RoutePlanPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/9 13:59
 */
public interface RoutePlanMapper {
    /**添加路线规划
     * @param routePlanPojos
     * @param routePlanDate
     * @param staff
     * @return
     */
    int insertRouteList(@Param("routePlanPojos") List<RoutePlanPojo> routePlanPojos,
                        @Param("routePlanDate") String routePlanDate,
                        @Param("staff") Staff staff);

    /**根据选择的职员获取所有的规划
     * @param staffId
     * @param companyId
     * @return
     */
    List<RoutePlan> selectAllRoutePlanByStaffId(@Param("staffId") String staffId,
                                                @Param("companyId") String companyId);

    /**完成规划
     * @param companyId
     * @param staffId
     * @param routePlanId
     * @return
     */
    int updateRoutePlanState(@Param("companyId")String companyId,
                             @Param("staffId") String staffId,
                             @Param("routePlanId")Integer routePlanId);

    /**根据日程ID获取规划详情
     * @param companyId
     * @param routePlanId
     * @return
     */
    RoutePlan selectRoutePlanByRoutePlanId(@Param("companyId")String companyId,
                                           @Param("routePlanId")Integer routePlanId);

    /**根据日期获取路程
     * @param staffId
     * @param date
     * @return
     */
    List<RoutePlan> selectRoutePlanByDate(@Param("staffId") String staffId, @Param("date") String date);
}
