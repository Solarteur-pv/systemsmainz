package dev.yukado.systemsmainz.service.user;

import dev.yukado.systemsmainz.audit.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomFailureHandler implements AuthenticationFailureHandler {

    private final AuditLogService auditLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String username = request.getParameter("username");
        String ip = request.getRemoteAddr();

        auditLogService.log(
                username != null ? username : "unknown",
                "LOGIN_FAILED",
                "/login",
                exception.getMessage(),
                ip
        );

        response.sendRedirect("/login?error");
    }
}

