package booksreview.services;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.UsersDto;
import booksreview.entities.Books;
import booksreview.entities.Reviews;
import booksreview.entities.Users;
import booksreview.exceptions.BookNotFoundException;
import booksreview.exceptions.UserNotFoundException;
import booksreview.mappers.BooksMapper;
import booksreview.mappers.UsersMapper;
import booksreview.repositories.BooksRepository;
import booksreview.repositories.ReviewsRepository;
import booksreview.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static booksreview.audit.AuditLogsStatus.NOT_SUCCESSFUL;
import static booksreview.audit.AuditLogsStatus.SUCCESSFUL;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final ReviewsRepository reviewsRepository;
    private final BooksService booksService;
    private final AuditLogsImpl auditLogsImplementation;

    public UsersService(UsersRepository usersRepository,
                        BooksRepository booksRepository,
                        ReviewsRepository reviewsRepository, BooksService booksService, AuditLogsImpl auditLogsImplementation) {
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
        this.reviewsRepository = reviewsRepository;
        this.booksService = booksService;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    public List<UsersDto> getAllUsers(int auditLogId) {
        List<UsersDto> usersDtoList = new ArrayList<>();
        usersRepository.findAll().forEach(user -> usersDtoList.add(UsersMapper.modelMapper.map(user, UsersDto.class))); // Mapper not ready
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
        return usersDtoList;
    }

    public UsersDto getUserById(int id, int auditLogId) {
        if(usersRepository.existsById(id)) {
            Users user = usersRepository.findById(id).get();
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return UsersMapper.modelMapper.map(user, UsersDto.class); // Mapper not ready
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new UserNotFoundException(id);
        }
    }

    public List<BooksDto> getBooksByUsersId(int id, int auditLogId) {
        if(usersRepository.existsById(id)) {
            Users user = usersRepository.findById(id).get();
            List<Books> booksListFilteredByUser = booksRepository.findAll().stream()
                    .filter(book -> book.getAddedByUser().getId() == (user.getId())).toList();
            user.setBooks(booksListFilteredByUser);
            List<BooksDto> listDto = new ArrayList<>();
                    booksListFilteredByUser.forEach(book -> listDto.add(BooksMapper.modelMapper.map(book, BooksDto.class)));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return listDto;
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new UserNotFoundException(id);
        }
    }

    public BooksDto getUsersReviewsByBookId(int userId, int bookId, int auditLogId) {
       if(booksRepository.existsById(bookId)) {
           Books book = booksRepository.findById(bookId).get();
          if(usersRepository.existsById(userId)) {
              Users user = usersRepository.findById(userId).get();
              List<Reviews> usersReviewsByBook = reviewsRepository.findAll().stream()
                      .filter(review -> review.getBook().getId() == bookId)
                      .filter(review -> review.getCreatedBy().getId() == user.getId()).toList();
              book.setReviews(usersReviewsByBook);
              auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
              return BooksMapper.modelMapper.map(book, BooksDto.class); //
          } else {
              auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
              throw  new UserNotFoundException(userId);
          }
       } else {
           auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
           throw  new BookNotFoundException(bookId);
       }
    }

    public UsersDto addNewUser(UsersDto userDto, int auditLogId) {
        Users newUser = UsersMapper.modelMapper.map(userDto, Users.class);
        usersRepository.save(newUser);
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
        return UsersMapper.modelMapper.map(newUser, UsersDto.class);
    }

    public UsersDto updateUserById(UsersDto userDto, int id, int auditLogId) {
        Users userToUpdate = UsersMapper.modelMapper.map(userDto, Users.class);
        if(usersRepository.existsById(id)) {
            Users userFromDb = usersRepository.findById(id).get(); //ToCheck for existing ID
            userFromDb.setEmail(userToUpdate.getEmail());
            userFromDb.setName(userToUpdate.getName());
            usersRepository.save(userFromDb);
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return UsersMapper.modelMapper.map(userFromDb, UsersDto.class);
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
           throw new UserNotFoundException(id);
        }
    }

    public void deleteUserById(int id, int auditLogId) {
        if(usersRepository.existsById(id)) {
            deleteReviewsByUserId(id);
            booksService.deleteBooksByUserId(id);
        }
        usersRepository.deleteById(id);
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
    }

    public void deleteReviewsByUserId(int id) {
        List<Reviews> reviewsToDelete = reviewsRepository.findAll().stream()
                .filter(review -> review.getCreatedBy().getId() == id).toList();
        if(!reviewsToDelete.isEmpty()) {
            reviewsToDelete.forEach(reviews -> reviewsRepository.deleteById(reviews.getId()));
        }
    }
}
