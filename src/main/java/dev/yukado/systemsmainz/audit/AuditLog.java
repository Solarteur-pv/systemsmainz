package dev.yukado.systemsmainz.audit;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String action;
    private String endpoint;
    private String details;
    private String ip;

    private LocalDateTime timestamp;
}
