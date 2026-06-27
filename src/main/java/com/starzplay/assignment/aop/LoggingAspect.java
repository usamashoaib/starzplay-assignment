package com.starzplay.assignment.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.StringJoiner;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /** Field names (lower-cased, substring match) whose values must never be logged. */
    private static final Set<String> SENSITIVE_FIELDS = Set.of("password", "secret", "token", "jwt");

    private static final String MASK = "****";

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
        if (data == null) {
            return null;
        }
        if (data instanceof Object[] array) {
            return Arrays.stream(array).map(this::maskSensitiveData).toArray();
        }
        if (data instanceof Collection<?> collection) {
            return collection.stream().map(this::maskSensitiveData).toList();
        }
        // Only introspect our own DTOs; framework/entity types keep their own toString()
        if (data.getClass().getName().startsWith("com.starzplay.assignment.dto")) {
            return maskObjectFields(data);
        }
        return data;
    }

    private String maskObjectFields(Object data) {
        Class<?> type = data.getClass();
        StringJoiner joiner = new StringJoiner(", ", type.getSimpleName() + "{", "}");
        for (Field field : type.getDeclaredFields()) {
            Object value;
            if (isSensitive(field.getName())) {
                value = MASK;
            } else {
                field.setAccessible(true);
                try {
                    value = field.get(data);
                } catch (IllegalAccessException e) {
                    value = "<inaccessible>";
                }
            }
            joiner.add(field.getName() + "=" + value);
        }
        return joiner.toString();
    }

    private boolean isSensitive(String fieldName) {
        String lower = fieldName.toLowerCase();
        return SENSITIVE_FIELDS.stream().anyMatch(lower::contains);
    }
}
