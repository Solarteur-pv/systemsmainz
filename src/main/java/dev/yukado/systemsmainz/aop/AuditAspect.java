package dev.yukado.systemsmainz.aop;

import dev.yukado.systemsmainz.audit.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogService auditLogService;
    private final HttpServletRequest request;

    @Around("@annotation(audit)")
    public Object logAction(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {

        Object result = joinPoint.proceed();

        String username = "anonymous";
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        String endpoint = request.getRequestURI();
        String ip = request.getRemoteAddr();

        String details = joinPoint.getSignature().toShortString();

        auditLogService.log(username, audit.action(), endpoint, details, ip);

        return result;
    }
}
