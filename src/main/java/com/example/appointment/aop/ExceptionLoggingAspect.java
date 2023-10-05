package com.example.appointment.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.example.appointment.service.*.*(..))", throwing = "error")
    public void afterThrowingAdvice(JoinPoint jp, Throwable error) {
        log.warn("Method Signature: " + jp.getSignature());
        log.warn("Method Signature: " + jp.getSignature(), error);
    }
}
