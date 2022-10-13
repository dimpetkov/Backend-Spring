package booksreview.entities.audit.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auditLogs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int logId;
    @CreationTimestamp
    Date createdAt;
    String requesterId;
    String requestBody;
    String responseStatus;

    public AuditLog(){}

    public AuditLog(String requesterId, String requestBody, String responseStatus) {
        setRequesterId(requesterId);
        setRequestBody(requestBody);
        setResponseStatus(responseStatus);
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
