package booksreview.services;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.entities.Books;
import booksreview.entities.Users;
import booksreview.exceptions.BookNotFoundException;
import booksreview.exceptions.RequestNotCorrect;
import booksreview.exceptions.UserNotFoundException;
import booksreview.mappers.BooksMapper;
import booksreview.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static booksreview.audit.AuditLogsStatus.NOT_SUCCESSFUL;
import static booksreview.audit.AuditLogsStatus.SUCCESSFUL;

@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final LanguagesService languagesService;
    private final CategoriesService categoriesService;
    private final AuthorsService authorsService;
    private final ReviewsService reviewsService;
    private final UsersRepository usersRepository;
    private final AuditLogsImpl auditLogsImplementation;

    public BooksService(BooksRepository booksRepository, LanguagesService languagesService,
                        CategoriesService categoriesService, AuthorsService authorsService,
                        ReviewsService reviewsService, UsersRepository usersRepository,
                        AuditLogsImpl auditLogsImplementation) {
        this.booksRepository = booksRepository;
        this.languagesService = languagesService;
        this.categoriesService = categoriesService;
        this.authorsService = authorsService;
        this.reviewsService = reviewsService;
        this.usersRepository = usersRepository;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    public List<BooksDto> getAllBooks(int auditLogId) {
        List<BooksDto> booksDtoList = new ArrayList<>();
        booksRepository.findAll().forEach(book -> booksDtoList.add(BooksMapper.modelMapper.map(book, BooksDto.class)));
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
        return booksDtoList;
    }

    public BooksDto getBookById(int id, int auditLogId) {
        if (booksRepository.existsById(id)) {
            Books book = booksRepository.findById(id).get();
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return BooksMapper.modelMapper.map(book, BooksDto.class);
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new BookNotFoundException(id);
        }
    }

    public BooksDto addNewBook(int userId, BooksDto bookDto, int auditLogId) {
        Books newBook = new Books();
        Books bookToAdd = BooksMapper.modelMapper.map(bookDto, Books.class);
        if(usersRepository.findById(userId).isPresent()) {
            newBook.setAddedByUser(usersRepository.findById(userId).get());
            newBook.setBookName(bookToAdd.getBookName());
            newBook.setBookAuthor(bookToAdd.getBookAuthor());
            newBook.setBookLanguage(bookToAdd.getBookLanguage());
            newBook.setBookYear(bookToAdd.getBookYear());
            newBook.setBookCategory(bookToAdd.getBookCategory());
            newBook.setArticleText(bookToAdd.getArticleText());
            booksRepository.save(newBook);
            updateAttributes(BooksMapper.modelMapper.map(newBook, BooksDto.class));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return BooksMapper.modelMapper.map(bookToAdd, BooksDto.class);
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new UserNotFoundException(userId);
        }
    }

    public BooksDto updateBookById(int userId, BooksDto bookDto, int bookId, int auditLogId) {
        Books bookToUpdate = BooksMapper.modelMapper.map(bookDto, Books.class);
        Users user = new Users();
        if(usersRepository.findById(userId).isPresent()) {
            user = usersRepository.findById(userId).get();
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new UserNotFoundException(userId);
        }
        if(booksRepository.existsById(bookId)) {
            Books bookFromDb = booksRepository.findById(bookId).get();
            bookFromDb.setAddedByUser(user);
            bookFromDb.setBookName(bookToUpdate.getBookName());
            bookFromDb.setBookAuthor(bookToUpdate.getBookAuthor());
            bookFromDb.setBookLanguage(bookToUpdate.getBookLanguage());
            bookFromDb.setBookYear(bookToUpdate.getBookYear());
            bookFromDb.setBookCategory(bookToUpdate.getBookCategory());
            bookFromDb.setArticleText(bookToUpdate.getArticleText());
            booksRepository.save(bookFromDb);
            updateAttributes(BooksMapper.modelMapper.map(bookFromDb, BooksDto.class));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return BooksMapper.modelMapper.map(bookFromDb, BooksDto.class);
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new BookNotFoundException(bookId);
        }
    }

    public void deleteBookById(int userId, int bookId, int auditLogId) {
        if(booksRepository.findById(bookId).isPresent()
                && usersRepository.findById(userId).isPresent()) {
            Books bookToDelete = booksRepository.findById(bookId).get();
            if(bookToDelete.getAddedByUser().getId() == userId) {
                reviewsService.deleteReviewsByBookId(bookId);
                booksRepository.deleteById(bookId);
                deleteAttributes(bookToDelete);
                auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            } else {
                auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
                throw new RequestNotCorrect();
            }
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new BookNotFoundException(bookId);
        }
    }

    public void deleteBooksByUserId(int id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        List<Books> booksToDelete =  booksRepository.findAll().stream()
                .filter(article -> article.getAddedByUser().getId() == (user.getId())).toList();
        if(!booksToDelete.isEmpty()) {
            booksToDelete.forEach(article -> deleteBooksByUser(article.getId()));
        }
    }

    private void deleteAttributes(Books bookToDelete) {
        languagesService.deleteLanguage(bookToDelete.getBookLanguage());
        authorsService.deleteAuthor(bookToDelete.getBookAuthor());
        categoriesService.deleteCategory(bookToDelete.getBookCategory());
        reviewsService.deleteReviewsByBookId(bookToDelete.getId());
    }

    private void updateAttributes(BooksDto bookDto) {
        languagesService.addNewLanguage(bookDto.getBookLanguage());
        categoriesService.addNewCategory(bookDto.getBookCategory());
        authorsService.addNewAuthor(bookDto.getBookAuthor());
    }

    public void deleteBooksByUser(int id) {
        if(booksRepository.existsById(id)) {
            Books bookToDelete = booksRepository.findById(id).get();
            booksRepository.deleteById(id);
            deleteAttributes(bookToDelete);
            String auditLogBody = "DELETE book: " + id;
            int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
        }
    }
}
