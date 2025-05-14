package com.Ashmo.CommentService.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("execution(* com.Ashmo.CommentService.Service..*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.Ashmo.CommentService.Controller..*(..))")
    public void controllerLayer() {}

    @Pointcut("execution(* com.Ashmo.CommentService.Filter..*(..))")
    public void filterLayer() {}

    @Pointcut("serviceLayer() || controllerLayer() || filterLayer()")
    public void allLayer() {}

    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("#######~~~~~~~#######");
        logger.info("Method " + joinPoint.getSignature().getName() + " executed in: " + executionTime + "ms");
        logger.info("#######~~~~~~~#######");
        return proceed;
    }

    @AfterThrowing(value = "allLayer()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.info("#######~~~~~~~#######");
        logger.error("Method name : " + joinPoint.getSignature().getName() + " Exception occurred: " + exception.getMessage());
        logger.info("#######~~~~~~~#######");
    }
}
