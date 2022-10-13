package booksreview.exceptions;

public class AuditLogNotFoundException extends RuntimeException {
    public AuditLogNotFoundException(int logId) {super("Not found AuditLog " + logId);}
}
