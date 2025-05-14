package com.Ashmo.BugService.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Pointcut("execution(* com.Ashmo.BugService.Controller..*(..))")
    public void controllerLayer() {}

    @Before("controllerLayer()")
    public void logSecurityContext(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("User: {} accessing method: {} in class: {}", 
                authentication.getName(),
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName());
        } else {
            logger.warn("Unauthenticated access attempt to method: {} in class: {}", 
                joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName());
        }
    }
} 