package booksreview.controllers;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.services.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {
    private final BooksService booksService;
    private final AuditLogsImpl auditLogsImplementation;

    public BooksController(BooksService service, AuditLogsImpl auditLogsImplementation) {
        this.booksService = service;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    @GetMapping("/books")
    public List<BooksDto> allArticles() {
        String auditLogBody = "GET all books";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return booksService.getAllBooks(auditLogId);
    }

    @GetMapping("/books/{id}")
    public BooksDto bookById(@PathVariable("id") int id) {
        String auditLogBody = "GET book ID: " + id;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return booksService.getBookById(id, auditLogId);
    }



    @PostMapping("/users/{userId}/books")
    public BooksDto addBook(@RequestBody BooksDto newBook,
                            @PathVariable int userId) {
        String auditLogBody = "ADD book";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return booksService.addNewBook(userId, newBook, auditLogId);
    }

    @PutMapping("users/{userId}/books/{bookId}")
    public BooksDto updateBook(@RequestBody BooksDto bookDto, @PathVariable("userId") int userId,
                               @PathVariable("bookId") int bookId) {
        String auditLogBody = "UPDATE book ID: " + bookId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return booksService.updateBookById(userId, bookDto, bookId, auditLogId);
    }

    @DeleteMapping("users/{userId}/books/{bookId}")
    HttpStatus deleteBook(@PathVariable("bookId") int bookId, @PathVariable("userId") int userId) {
        String auditLogBody = "DELETE book ID: " + bookId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        booksService.deleteBookById(userId, bookId, auditLogId);
        return HttpStatus.OK;
    }

}
