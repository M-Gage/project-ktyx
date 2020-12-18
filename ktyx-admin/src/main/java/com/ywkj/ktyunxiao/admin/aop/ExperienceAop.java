package com.ywkj.ktyunxiao.admin.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ywkj.ktyunxiao.common.annotation.Exp;
import com.ywkj.ktyunxiao.common.enums.CacheName;
import com.ywkj.ktyunxiao.common.enums.ExpType;
import com.ywkj.ktyunxiao.common.enums.FollowOpinionType;
import com.ywkj.ktyunxiao.common.exception.NoticeException;
import com.ywkj.ktyunxiao.common.exception.ParamException;
import com.ywkj.ktyunxiao.common.utils.JPushUtil;
import com.ywkj.ktyunxiao.common.utils.StringUtil;
import com.ywkj.ktyunxiao.model.Experience;
import com.ywkj.ktyunxiao.model.ExperienceConfig;
import com.ywkj.ktyunxiao.model.Staff;
import com.ywkj.ktyunxiao.model.pojo.FollowOpinionPojo;
import com.ywkj.ktyunxiao.service.ExperienceConfigService;
import com.ywkj.ktyunxiao.service.ExperienceService;
import com.ywkj.ktyunxiao.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author LiWenHao
 * @date 2018/07/03 11:23
 */
@Slf4j
@Aspect
@Component
public class ExperienceAop {

    @Resource
    private CacheManager cacheManager;

    @Autowired
    private ExperienceConfigService experienceConfigService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private JPushUtil jPushUtil;

    private final String[] operator = {"+","-"};

    @Pointcut("@annotation(com.ywkj.ktyunxiao.common.annotation.Exp)")
    public void expCut(){}

    @AfterReturning(pointcut = "expCut()")
    public void cutAfter(JoinPoint joinPoint) {
        log.info("记录经验值");
        Staff staff = (Staff) getRequest().getAttribute("staff");
        if (staff == null) {
            staff = (Staff) getRequest().getSession().getAttribute("staff");
        }
        Experience experience = null;
        log.info("查询缓存");
        Cache experienceCache = cacheManager.getCache(CacheName.EXPERIENCE.getCacheName());
        String cacheValue = experienceCache.get(staff.getCompanyId(), String.class);
        ExperienceConfig experienceConfig = null;
        if (StringUtil.isNotEmpty(cacheValue)) {
            log.info("命中经验值缓存");
            experienceConfig = JSON.parseObject(cacheValue, ExperienceConfig.class);
        } else {
            log.info("缓存不存在查找数据");
            experienceConfig = experienceConfigService.selectByCompanyId(staff.getCompanyId());
            log.info("插入缓存");
            experienceCache.put(staff.getCompanyId(), JSON.toJSONString(experienceConfig));
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Exp annotation = methodSignature.getMethod().getAnnotation(Exp.class);
        //方法值
        Object[] args = joinPoint.getArgs();
        //之后的经验
        int afterExp = 0;
        //职员Id
        String staffId = staff.getStaffId();
        //获取跟进归属人Id
        switch (annotation.value()) {
            case INSERT_FOLLOW_OPINION:
                staffId = JSONObject.parseObject(JSON.toJSONString(args[0]), FollowOpinionPojo.class).getFollowStaffId();
                break;
            case DELETE_FOLLOW_OPINION:
                staffId = args[3].toString();
                break;
        }
        //之前经验值
        int beforeExp = staffService.selectExperienceByStaffId(staffId, staff.getCompanyId());
        switch (annotation.value()) {
            case INSERT_CUSTOMER:
                experience = new Experience(ExpType.INSERT_CUSTOMER.getName(), operator[0] + experienceConfig.getInsertCustomer());
                afterExp = beforeExp + experienceConfig.getInsertCustomer();
                break;
            case INSERT_ORDER:
                experience = new Experience(ExpType.INSERT_ORDER.getName(), operator[0] + experienceConfig.getInsertOrder());
                afterExp = beforeExp + experienceConfig.getInsertOrder();
                break;
            case INSERT_FOLLOW:
                experience = new Experience(ExpType.INSERT_FOLLOW.getName(), operator[0] + experienceConfig.getFollowLike());
                afterExp = beforeExp + experienceConfig.getFollowLike();
                break;
            case INSERT_FOLLOW_OPINION:
                switch ((int) args[1]) {
                    case 0:
                        experience = new Experience(String.format(ExpType.INSERT_FOLLOW_OPINION.getName(), FollowOpinionType.DIS_LIKE.getName()), operator[1] + experienceConfig.getFollowDislike());
                        afterExp = beforeExp - experienceConfig.getFollowDislike();
                        break;
                    case 1:
                        experience = new Experience(String.format(ExpType.INSERT_FOLLOW_OPINION.getName(), FollowOpinionType.LIKE.getName()), operator[0] + experienceConfig.getFollowLike());
                        afterExp = beforeExp + experienceConfig.getFollowLike();
                        jPushUtil.send(jPushUtil.alis(staff.getStaffName() + "赞了你的跟进", staffId));
                        break;
                }
                break;
            case DELETE_FOLLOW_OPINION:
                switch ((int) args[4]) {
                    case 0:
                        experience = new Experience(String.format(ExpType.DELETE_FOLLOW_OPINION.getName(), FollowOpinionType.DIS_LIKE.getName()), operator[0] + experienceConfig.getFollowDislike());
                        afterExp = beforeExp + experienceConfig.getFollowDislike();
                        break;
                    case 1:
                        experience = new Experience(String.format(ExpType.DELETE_FOLLOW_OPINION.getName(), FollowOpinionType.LIKE.getName()), operator[1] + experienceConfig.getFollowLike());
                        afterExp = beforeExp - experienceConfig.getFollowLike();
                        break;
                }
                break;
        }
        if (experience == null) {
            throw new ParamException();
        }
        experience.setCompanyId(staff.getCompanyId());
        experience.setStaffId(staffId);
        experience.setBefore(beforeExp);
        experience.setAfter(afterExp);
        if (!experienceService.insert(experience)) {
            throw new NoticeException("经验值增加失败!");
        }
        if (!staffService.updateExperienceByStaffId(staffId, staff.getCompanyId(), afterExp)) {
            throw new NoticeException("经验值增加失败!");
        }
    }

    public HttpServletRequest getRequest(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        return requestAttributes.getRequest();
    }

}