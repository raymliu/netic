package com.rays.componment.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hand on 2017/3/26.
 */
@Aspect
@Component
public class HttpAspect {
    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(* com.rays.web.UserResource.*(*))")
    public void log(){}

    @Around("log()")
    public Object getRequestLog(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuffer requestURL = request.getRequestURL();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();

        String reqClass = joinPoint.getSignature().getDeclaringTypeName(); //类名
        String reqMethod = joinPoint.getSignature().getName(); //请求方法
        Object[] args1 = joinPoint.getArgs();
        //记录请求日志
        log.info("Enter:{}.{} with argments = {}", reqClass, reqMethod, args1.toString());
        log.info("Request URL: {},with Method {},ip:{}",requestURL,method,ip);

        Object result = joinPoint.proceed();
        log.info("Exit:{}.{} with result = {}", joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName(), result);
        return result ;
    }
}

