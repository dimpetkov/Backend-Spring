package booksreview.controllers;
import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.LanguagesDto;
import booksreview.services.LanguagesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguagesController {
    private final LanguagesService languagesService;
    private final AuditLogsImpl auditLogsImplementation;

    public LanguagesController(LanguagesService languagesService, AuditLogsImpl auditLogsImplementation) {
        this.languagesService = languagesService;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    @GetMapping("/languages")
    public List<LanguagesDto> getAllLanguages() {
        String auditLogBody = "GET all languages";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return languagesService.getLanguagesList(auditLogId);
    }

    @GetMapping("/languages/{languageId}")
    public List<BooksDto> getArticlesByLanguage(@PathVariable int languageId) {
        String auditLogBody = "GET language ID " + languageId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return languagesService.getArticlesListByLanguageId(languageId, auditLogId);
    }
}
