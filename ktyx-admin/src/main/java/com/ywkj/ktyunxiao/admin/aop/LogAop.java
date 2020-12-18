package com.ywkj.ktyunxiao.admin.aop;

import com.alibaba.fastjson.JSON;
import com.ywkj.ktyunxiao.dao.LogMapper;
import com.ywkj.ktyunxiao.model.Log;
import com.ywkj.ktyunxiao.model.pojo.StaffPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MiaoGuoZhu
 * @date 2018/6/4 10:29
 */
@Aspect
@Component
@Slf4j
public class LogAop {

    @Autowired
    private LogMapper logMapper;

    @Pointcut("execution(* com.ywkj.ktyunxiao.*.controller..*.*(..))")
    public void point() {
    }

    @Before(value = "point()")
    public void cutBefore(JoinPoint joinPoint) throws Exception {
        //获取参数名称和值
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), joinPoint);
    }

    @AfterReturning(pointcut = "point()", returning = "returnValue")
    public void cutAfter(JoinPoint joinPoint, Object returnValue) throws Exception {

        //获取参数名称和值
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), joinPoint);

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = request.getSession();
        StaffPojo staff = (StaffPojo) session.getAttribute("staff");


        //插入日志
        if (staff != null) {
            String url = request.getRequestURL().toString().replace("//","");
            Log l = new Log(staff.getStaffId()
                    , url.substring(url.indexOf("/"),url.length())
                    , nameAndArgs == null ? null : nameAndArgs.toString()
                    , request.getMethod()
                    , request.getHeader("user-agent")
                    , request.getRemoteAddr()
                    , new Date());
            logMapper.insertOperationLog(l);
        }

    }

    /**得到传入参数名称及其内容
     * @param cls
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private Map<String, Object> getFieldsName(Class cls, JoinPoint joinPoint) throws Exception {
        Class<?> clazz = Class.forName(joinPoint.getTarget().getClass().getName());
        String clazzName = clazz.getName();

        Object[] args = joinPoint.getArgs();
        Map<String, Object> map = new HashMap<>(16);

        //获取在代码属性信息中返回指定名称的属性
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(cls));
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(joinPoint.getSignature().getName());
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        if (attr != null) {
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < cm.getParameterTypes().length; i++) {
                if ( args[i]!=null){
                    String replace = args[i].toString().replace("=null", "");
                    //paramNames即参数名
                    map.put(attr.variableName(i + pos), replace);
                }
            }
        }
        return map;
    }
}
