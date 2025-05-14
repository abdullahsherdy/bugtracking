package com.Ashmo.UserService.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.Ashmo.UserService.Controller..*(..))")
    public void controllerLayer() {}

    @Pointcut("execution(* com.Ashmo.UserService.Service..*(..))")
    public void serviceLayer() {}

    @Before("controllerLayer() || serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} in class: {}", 
            joinPoint.getSignature().getName(),
            joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("controllerLayer() || serviceLayer()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {} in class: {}", 
            joinPoint.getSignature().getName(),
            joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterReturning(pointcut = "controllerLayer() || serviceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method: {} returned with value: {}", 
            joinPoint.getSignature().getName(), 
            result);
    }
} 