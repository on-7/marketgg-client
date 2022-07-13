package com.nhnacademy.marketgg.client.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* com..adapter.impl.*Adapter.*(..))")
    private void cut() {
    }

    @Before("cut()")
    public void logging(JoinPoint joinPoint) {
        String method = getMethod(joinPoint).split(".impl.")[1];
        log.info("Method Name = {}", method);
    }

    private String getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().toString();
    }

}