package booksreview.controllers;
import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.AuthorsDto;
import booksreview.services.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class AuthorsController {
    private final AuthorsService authorsService;
    private final AuditLogsImpl auditLogsImplementation;

    public AuthorsController(AuthorsService authorsService, AuditLogsImpl auditLogsImplementation) {
        this.authorsService = authorsService;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    @GetMapping("/authors")
    public List<AuthorsDto> getAllAuthors() {
        String auditLogBody = "GET all authors";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return authorsService.getAuthorsList(auditLogId);
    }

    @GetMapping("/authors/{id}")
    public List<BooksDto> getArticlesByAutor(@PathVariable int id) {
        String auditLogBody = "GET author ID " + id;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return authorsService.getAuthorsListByAuthorID(id, auditLogId);
    }
}
