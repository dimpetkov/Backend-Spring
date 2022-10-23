package booksreview.services;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.ReviewsDto;
import booksreview.entities.Books;
import booksreview.entities.Reviews;
import booksreview.exceptions.BookNotFoundException;
import booksreview.exceptions.RequestNotCorrect;
import booksreview.exceptions.ReviewNotFoundException;
import booksreview.mappers.ReviewsMapper;
import booksreview.repositories.BooksRepository;
import booksreview.repositories.ReviewsRepository;
import booksreview.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static booksreview.audit.AuditLogsStatus.NOT_SUCCESSFUL;
import static booksreview.audit.AuditLogsStatus.SUCCESSFUL;

@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final AuditLogsImpl auditLogsImplementation;

    public ReviewsService(ReviewsRepository reviewsRepository, UsersRepository usersRepository, BooksRepository booksRepository, AuditLogsImpl auditLogsImplementation) {
        this.reviewsRepository = reviewsRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    public List<ReviewsDto> getAllReviewsByBookId(int id, int auditLogId) {
        if(booksRepository.existsById(id)) {
            Books book = booksRepository.findById(id).get();

            List<ReviewsDto> reviewsDtoList = new ArrayList<>();
            book.getReviews().forEach(review -> reviewsDtoList.add(ReviewsMapper.modelMapper.map(review, ReviewsDto.class)));

            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return reviewsDtoList;
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new BookNotFoundException(id);

        }
    }

    public ReviewsDto getReviewByIdByBook(int bookId, int reviewId, int auditLogId) {
        if(reviewsRepository.existsById(reviewId)) {
            Reviews review = reviewsRepository.findById(reviewId).get();
            if (review.getBook().getId() == bookId) {
                auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
                return ReviewsMapper.modelMapper.map(review, ReviewsDto.class);
            } else {
                auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
                throw new ReviewNotFoundException(reviewId);
            }
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public ReviewsDto updateReview(int userId, int bookId, ReviewsDto reviewDto, int id, int auditLogId) {
        Reviews reviewToUpdate = ReviewsMapper.modelMapper.map(reviewDto, Reviews.class);
        if(reviewsRepository.existsById(id)) {
            Reviews reviewFromDb = reviewsRepository.findById(id).get();
            if(reviewFromDb.getCreatedBy().getId() == userId
                    && reviewFromDb.getBook().getId() == bookId) {
                reviewFromDb.setReviewText(reviewToUpdate.getReviewText());
                reviewsRepository.save(reviewFromDb);
                auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
                return ReviewsMapper.modelMapper.map(reviewFromDb, ReviewsDto.class);
            } else {
                auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
                throw new RequestNotCorrect();
            }
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new ReviewNotFoundException(id);
        }
    }

    public ReviewsDto addNewReview(int userId, int bookId, ReviewsDto reviewDto, int auditLogId) {
        Reviews newReview = new Reviews();
        Reviews reviewToAdd = ReviewsMapper.modelMapper.map(reviewDto, Reviews.class);
        if(usersRepository.existsById(userId) && booksRepository.existsById(bookId)) {

            newReview.setBook(booksRepository.findById(bookId).get());
            newReview.setCreatedBy(usersRepository.findById(userId).get());
            newReview.setReviewText(reviewToAdd.getReviewText());
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            reviewsRepository.save(newReview);
            return ReviewsMapper.modelMapper.map(newReview, ReviewsDto.class);
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new RequestNotCorrect();
        }
    }

    public void deleteReview(int userId, int bookId, int reviewId, int auditLogId) {
        if(reviewsRepository.existsById(reviewId)) {
            Reviews reviewToDelete = reviewsRepository.findById(reviewId).get();

            if(reviewToDelete.getCreatedBy().getId() == userId
                 && reviewToDelete.getBook().getId() == bookId) {
                 reviewsRepository.deleteById(reviewId);
                 auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            } else {
                 auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
                 throw new RequestNotCorrect();
            }
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public void deleteReviewsByBookId(int bookId) {
        List<Reviews> reviewsToDelete = reviewsRepository.findAll()
                .stream().filter(review -> (review.getBook().getId()) == bookId).toList();
        if(!reviewsToDelete.isEmpty()) {
            reviewsToDelete.forEach(review -> reviewsRepository.deleteById(review.getId()));
        }
    }


}
