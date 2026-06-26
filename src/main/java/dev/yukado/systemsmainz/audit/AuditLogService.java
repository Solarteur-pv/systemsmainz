package dev.yukado.systemsmainz.audit;

public interface AuditLogService {
    void log(String username, String action, String endpoint, String details, String ip);
}
