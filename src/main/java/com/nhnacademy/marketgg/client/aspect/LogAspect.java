package com.nhnacademy.marketgg.client.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * AOP 를 처리하기 위한 클래스입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * AOP 접근이 될 위치를 지정합니다.
     *
     * @since 1.0.0
     */
    @Pointcut("execution(* com..repository.impl.*Adapter.*(..))")
    private void cut() {
    }

    /**
     * JoinPoint 에 접근한 메소드 명을 로그에 남깁니다.
     *
     * @param joinPoint - AOP 접근된 메소드의 위치입니다.
     * @since 1.0.0
     */
    @Before("cut()")
    public void logging(JoinPoint joinPoint) {
        String method = getMethod(joinPoint).split(".impl.")[1];
        log.info("Method Name = {}", method);
    }

    /**
     *
     *
     * @param joinPoint - AOP 에 접근된 메소드의 위치입니다.
     * @return JoinPoint 에 접근한 메소드 명을 반환합니다.
     * @since 1.0.0
     */
    private String getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().toString();
    }

}