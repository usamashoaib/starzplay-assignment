package com.starzplay.assignment.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("within(com.starzplay.assignment..*) && !within(com.starzplay.assignment.security..*)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        // Log method entry with arguments
        log.info("In {} with arguments: {}", methodName, maskSensitiveData(methodArgs));

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        // Log method exit with return value and execution time
        log.info("Out {} with result: {}. Execution time: {} ms",
                methodName, maskSensitiveData(result), (endTime - startTime));

        return result;
    }

    private Object maskSensitiveData(Object data) {
        // TODO: Not working need to check
        if (data instanceof String && ((String) data).contains("password")) {
            return "****";
        } else if (data instanceof Object[]) {
            return Arrays.stream((Object[]) data)
                    .map(this::maskSensitiveData)
                    .toArray();
        }
        return data;
    }
}