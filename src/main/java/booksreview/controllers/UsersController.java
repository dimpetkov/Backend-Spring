package booksreview.controllers;
import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.UsersDto;
import booksreview.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {
    private final UsersService usersService;
    private final AuditLogsImpl auditLogsImplementation;

    public UsersController(UsersService usersService, AuditLogsImpl auditLogsImplementation) {
        this.usersService = usersService;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    @GetMapping("/users")
    public List<UsersDto> allUsers() {
        String auditLogBody = "GET all users";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return usersService.getAllUsers(auditLogId);
    }

    @GetMapping("/users/{id}")
    public UsersDto getUserById(@PathVariable int id) {
        String auditLogBody = "GET user ID: " + id;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return usersService.getUserById(id, auditLogId);
    }

    @GetMapping("/users/{userId}/books")
    public List<BooksDto> getBooksByUserId(@PathVariable int userId) {
        String auditLogBody = "GET all books user ID: " + userId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return usersService.getBooksByUsersId(userId, auditLogId);
    }

    @GetMapping("/users/{userId}/books/{bookId}")
    public BooksDto getUsersReviewsByBook(@PathVariable int userId, @PathVariable int bookId) {
        String auditLogBody = "GET book ID " + bookId + " by user ID: " + userId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return usersService.getUsersReviewsByBookId(userId, bookId, auditLogId);
    }

    @PostMapping("/users")
    public UsersDto addNewUser(@RequestBody UsersDto userDto) {
        String auditLogBody = "ADD user";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return usersService.addNewUser(userDto, auditLogId);
    }

    @PutMapping("/users/{id}")
    public UsersDto updateUser(@RequestBody UsersDto userDto, @PathVariable int id) {
        String auditLogBody = "UPDATE user ID: " + id;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return usersService.updateUserById(userDto, id, auditLogId);
    }

    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUserById(@PathVariable int id) {
        String auditLogBody = "DELETE user ID: " + id;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        usersService.deleteUserById(id, auditLogId);
        return HttpStatus.OK;
    }

}
