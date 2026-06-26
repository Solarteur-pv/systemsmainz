package dev.yukado.systemsmainz.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repo;

    @Override
    public void log(String username, String action, String endpoint, String details, String ip) {

        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setEndpoint(endpoint);
        log.setDetails(details);
        log.setIp(ip);
        log.setTimestamp(LocalDateTime.now());

        repo.save(log);
    }
}

