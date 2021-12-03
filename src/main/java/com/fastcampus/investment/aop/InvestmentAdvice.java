package com.fastcampus.investment.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class InvestmentAdvice {
    @Before("com.fastcampus.investment.aop.InvestmentPointcut.apiPointcut()")
    public void beforeControllerLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        System.out.println("[ API ] " + method +
                "() 매개변수 : " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "com.fastcampus.investment.aop.InvestmentPointcut.apiPointcut()", returning = "returnObj")
    public void afterControllerLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();
        if(returnObj != null)
            System.out.println("[ API ] " + method + "() 리턴값 : " + returnObj);
    }

    @Before("com.fastcampus.investment.aop.InvestmentPointcut.servicePointcut()")
    public void beforeServiceLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        System.out.println("[ 서비스 ] " + method +
                "() 매개변수 : " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "com.fastcampus.investment.aop.InvestmentPointcut.servicePointcut()", returning = "returnObj")
    public void afterServiceLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();
        if(returnObj != null)
            System.out.println("[ 서비스 ] " + method + "() 리턴값 : " + returnObj);
    }

    @Before("com.fastcampus.investment.aop.InvestmentPointcut.repositoryPointcut()")
    public void beforeRepositoryLog(JoinPoint jp) {
        String method = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        System.out.println("[ 레파지토리 ] " + method +
                "() 매개변수 : " + Arrays.toString(args));
    }

    @AfterReturning(pointcut = "com.fastcampus.investment.aop.InvestmentPointcut.repositoryPointcut()", returning = "returnObj")
    public void afterRepositoryLog(JoinPoint jp, Object returnObj) {
        String method = jp.getSignature().getName();
        if(returnObj != null)
            System.out.println("[ 레파지토리 ] " + method + "() 리턴값 : " + returnObj);
    }
}
