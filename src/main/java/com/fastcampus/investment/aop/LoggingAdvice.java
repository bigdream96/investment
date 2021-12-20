package com.fastcampus.investment.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.lang.String.format;

@Slf4j
@Component
@Aspect
public class LoggingAdvice {

    @Before("com.fastcampus.investment.aop.InvestmentPointcut.apiPointcut()")
    public void beforeControllerLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug(format("[ API ] %s () 매개변수 : %s", method, Arrays.toString(args)));
    }

    @AfterReturning(pointcut = "com.fastcampus.investment.aop.InvestmentPointcut.apiPointcut()", returning = "returnObj")
    public void afterControllerLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();
        if(returnObj != null)
            log.debug(format("[ API ] %s () 리턴값 : %s", method, returnObj));
    }

    @Before("com.fastcampus.investment.aop.InvestmentPointcut.servicePointcut()")
    public void beforeServiceLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug(format("[ 서비스 ] %s () 매개변수 : %s", method, Arrays.toString(args)));
    }

    @AfterReturning(pointcut = "com.fastcampus.investment.aop.InvestmentPointcut.servicePointcut()", returning = "returnObj")
    public void afterServiceLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();
        if(returnObj != null)
            log.debug(format("[ 서비스 ] %s () 리턴값 : %s", method, returnObj));
    }

    @Before("com.fastcampus.investment.aop.InvestmentPointcut.repositoryPointcut()")
    public void beforeRepositoryLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        log.debug(format("[ 레파지토리 ] %s () 매개변수 : %s", method, Arrays.toString(args)));
    }

    @AfterReturning(pointcut = "com.fastcampus.investment.aop.InvestmentPointcut.repositoryPointcut()", returning = "returnObj")
    public void afterRepositoryLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();
        if(returnObj != null)
            log.debug(format("[ 레파지토리 ] %s () 리턴값 : %s", method, returnObj));
    }
}
