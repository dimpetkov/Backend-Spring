package booksreview.audit;

import booksreview.entities.audit.entity.AuditLog;
import booksreview.exceptions.AuditLogNotFoundException;
import booksreview.repositories.AuditLogsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuditLogsImpl {

    private final AuditLogsRepository auditLogsRepository;

    public AuditLogsImpl(AuditLogsRepository auditLogsRepository) {
        this.auditLogsRepository = auditLogsRepository;
    }

    public int createAuditLog(String requestBody) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String requesterId = request.getRemoteAddr();

        AuditLog newAuditLogEntity = new AuditLog();
        newAuditLogEntity.setRequestBody(requestBody);
        newAuditLogEntity.setRequesterId(requesterId);

        auditLogsRepository.save(newAuditLogEntity);
        return newAuditLogEntity.getLogId();
    }

    public void updateAuditLogStatus(int auditLogId, String status) {
        if(auditLogsRepository.existsById(auditLogId)) {
            AuditLog auditLogEntity = auditLogsRepository.findById(auditLogId).get();
            auditLogEntity.setResponseStatus(status);
            auditLogsRepository.save(auditLogEntity);
        } else {
            throw new AuditLogNotFoundException(auditLogId);
        }
    }
}
