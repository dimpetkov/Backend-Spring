package booksreview.controllers;
import booksreview.audit.AuditLogsImpl;
import booksreview.dto.ReviewsDto;
import booksreview.services.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewsController {
    private final ReviewsService reviewsService;
    private final AuditLogsImpl auditLogsImplementation;

    public ReviewsController(ReviewsService reviewsService, AuditLogsImpl auditLogsImplementation) {
        this.reviewsService = reviewsService;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    @GetMapping("/books/{bookId}/reviews")
    public List<ReviewsDto> allReviewsByArticleId(@PathVariable("bookId") int bookId) {
        String auditLogBody = "GET book ID: " + bookId + " reviews";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return reviewsService.getAllReviewsByBookId(bookId, auditLogId);
    }

    @GetMapping("/books/{bookId}/reviews/{reviewId}")
    public ReviewsDto singleReviewByIdByArticleId(@PathVariable("bookId") int bookId,
                                                  @PathVariable("reviewId") int reviewId) {
        String auditLogBody = "GET book ID: " + bookId + "review ID: " + reviewId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return reviewsService.getReviewByIdByBook(bookId, reviewId, auditLogId);
    }

    @GetMapping("users/{userId}/books/{bookId}/reviews")
    public List<ReviewsDto> userGetAllReviewsByArticleId(@PathVariable("userId") int userId,  @PathVariable("bookId") int bookId) {
        String auditLogBody = "user ID: " + userId + "GET book ID: " + bookId + " reviews";
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return reviewsService.getAllReviewsByBookId(bookId, auditLogId);
    }

    @GetMapping("users/{userId}/books/{bookId}/reviews/{reviewId}")
    public ReviewsDto userGetSingleReviewByIdByArticleId(@PathVariable("userId") int userId,
                                                         @PathVariable("bookId") int bookId,
                                                         @PathVariable("reviewId") int reviewId) {
        String auditLogBody = "user ID: " + userId+ "GET book ID: " + bookId + "review ID: " + reviewId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return reviewsService.getReviewByIdByBook(bookId, reviewId, auditLogId);
    }

    @PostMapping("users/{userId}/books/{bookId}/reviews")
    ReviewsDto addReview(@RequestBody ReviewsDto reviewToAdd,
                         @PathVariable("userId") int userId,
                         @PathVariable("bookId") int bookId) {
        String auditLogBody = "user ID: " + userId + "Add review for book ID: " + bookId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return reviewsService.addNewReview(userId, bookId, reviewToAdd, auditLogId);
    }

    @PutMapping("users/{userId}/books/{bookId}/reviews/{reviewId}")
    ReviewsDto updateReviewById(@RequestBody ReviewsDto reviewToUpdate,
                                @PathVariable("userId") int userId,
                                @PathVariable("bookId") int bookId,
                                @PathVariable("reviewId") int reviewId) {
        String auditLogBody = "user ID: " + userId + "UPDATE review ID " + reviewId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        return reviewsService.updateReview(userId, bookId, reviewToUpdate, reviewId, auditLogId);
    }

    @DeleteMapping("users/{userId}/books/{bookId}/reviews/{reviewId}")
    HttpStatus deleteReview(@PathVariable("userId") int userId, @PathVariable("bookId") int bookId, @PathVariable("reviewId") int reviewId) {
        String auditLogBody = "user ID: " + userId + "DELETE review ID " + reviewId;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        reviewsService.deleteReview(userId, bookId, reviewId, auditLogId);
        return HttpStatus.OK;
    }

}
