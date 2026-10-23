package com.online.shopping_app.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

    private static final Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    // Matches all methods inside your controller package
    @Pointcut("execution(* com.online.shopping_app.controller..*.*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
    	
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("Entering --> {}()", methodName);
        long startTime = System.currentTimeMillis();
        
        try {
        	
            Object result = joinPoint.proceed(); // Executes the controller method
            
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("Exiting --> {}() (Execution time: {} ms)", methodName, elapsedTime);
            
            return result;
        } catch (Exception e) {
        	
            logger.error("Exception in --> {}() --> message: {}", methodName, e.getMessage());
            throw e; // Rethrows the exception so your @ControllerAdvice / Global Exception Handler returns the 404 response
        }
    }
}
