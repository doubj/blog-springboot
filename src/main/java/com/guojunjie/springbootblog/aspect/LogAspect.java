package com.guojunjie.springbootblog.aspect;

import com.guojunjie.springbootblog.entity.Log;
import com.guojunjie.springbootblog.service.LogService;
import com.guojunjie.springbootblog.util.RequestHolder;
import com.guojunjie.springbootblog.util.StringUtils;
import com.guojunjie.springbootblog.util.ThrowableUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date： 2020/4/26 10:20
 * @author： guojunjie
 */
@Component
@Aspect
public class LogAspect {

    private final LogService logService;

    private long currentTime = 0L;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.guojunjie.springbootblog.aop.log.Log)")
    public void logPointcut() {

    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime);
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.addLog(StringUtils.getIp(request),joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime);
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.addLog(StringUtils.getIp(request), (ProceedingJoinPoint)joinPoint, log);
    }
}