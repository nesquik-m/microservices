package com.example.aop;

import com.example.annotation.LogAspect;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("@annotation(logAspect)")
    public void logMethod(JoinPoint joinPoint, LogAspect logAspect) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        if (logAspect.type() == LogType.CONTROLLER) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String httpMethod = request.getMethod();
            String requestURL = request.getRequestURI();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Anonymous";

            log.info("CONTROLLER {} | UserID: {} | Method: {} | URL: {} | HTTP Method: {} | Args: {}",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    username,
                    methodName,
                    requestURL,
                    httpMethod,
                    args);
        }
    }

}