package com.Ashmo.BugService.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);
    private static final long SLOW_EXECUTION_THRESHOLD = 1000; // 1 second

    @Pointcut("execution(* com.Ashmo.BugService.Service..*(..))")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        if (executionTime > SLOW_EXECUTION_THRESHOLD) {
            logger.warn("Slow execution detected in method: {} - Execution time: {}ms", 
                joinPoint.getSignature().getName(), 
                executionTime);
        } else {
            logger.info("Method: {} executed in {}ms", 
                joinPoint.getSignature().getName(), 
                executionTime);
        }

        return result;
    }
} 