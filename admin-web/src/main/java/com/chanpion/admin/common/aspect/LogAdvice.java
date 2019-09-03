package com.chanpion.admin.common.aspect;

import com.chanpion.admin.common.anno.Log;
import com.chanpion.admin.common.util.ShiroUtil;
import com.chanpion.admin.common.util.SpringUtil;
import com.chanpion.admin.entity.SysLog;
import com.chanpion.admin.entity.SysUser;
import com.chanpion.admin.service.ISysLogService;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 正常业务日志记录
 *
 * @author Administrator
 */
@Aspect
@Component
public class LogAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(LogAdvice.class);

    @Pointcut("@annotation(com.chanpion.admin.common.anno.Log)")
    public void controllerAspect() {

    }

    /**
     * 当方法正常返回是执行
     *
     * @param joinPoint
     */
    @AfterReturning("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Log log = method.getAnnotation(Log.class);
        SysUser sysUser = ShiroUtil.getSessionUser();
        if (log != null) {
            SysLog sysLog = new SysLog();
            sysLog.setCreateTime(new Date());
            sysLog.setTitle(log.value());
            sysLog.setUserName((sysUser != null) ? sysUser.getUserName() : "system");
            sysLog.setUrl(request.getRequestURI());
            sysLog.setParams(new Gson().toJson(request.getParameterMap()));
            SpringUtil.getBean(ISysLogService.class).save(sysLog);
            LOG.debug("记录日志:" + sysLog.toString());
        }
    }
}
