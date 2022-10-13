package booksreview.controllers;
import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.CategoriesDto;
import booksreview.services.CategoriesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CategoriesController {
    private final CategoriesService categoriesService;
    private final AuditLogsImpl auditLogsImplementation;

    public CategoriesController(CategoriesService categoriesService, AuditLogsImpl auditLogsImplementation) {
        this.categoriesService = categoriesService;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    @GetMapping("/categories")
    public List<CategoriesDto> getAllCategories() {
        String auditLogBody = "GET all categories";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return categoriesService.getCategoriesList(auditLogId);
    }

    @GetMapping("/categories/{id}")
    public List<BooksDto> getArticlesByCategory(@PathVariable int id) {
        String auditLogBody = "GET category ID " + id;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return categoriesService.getArticlesListByCategoryId(id, auditLogId);
    }
}
