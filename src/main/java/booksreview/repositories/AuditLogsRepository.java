package booksreview.repositories;

import booksreview.entities.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogsRepository extends JpaRepository<AuditLog, Integer> {
}
