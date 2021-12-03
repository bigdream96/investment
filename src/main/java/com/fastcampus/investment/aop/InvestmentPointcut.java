package com.fastcampus.investment.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class InvestmentPointcut {
    @Pointcut("execution(* com.fastcampus.investment.api..*.*(..))")
    public void apiPointcut() {}

    @Pointcut("execution(* com.fastcampus.investment.service..*.*(..))")
    public void servicePointcut() {}

    @Pointcut("execution(* com.fastcampus.investment.repository..*.*(..))")
    public void repositoryPointcut() {}
}
