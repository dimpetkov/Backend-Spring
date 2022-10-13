package booksreview.services;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.AuthorsDto;
import booksreview.entities.Books;
import booksreview.entities.Authors;
import booksreview.exceptions.AuthorNotFoundException;
import booksreview.mappers.BooksMapper;
import booksreview.mappers.AuthorsMapper;
import booksreview.repositories.BooksRepository;
import booksreview.repositories.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static booksreview.audit.AuditLogsStatus.NOT_SUCCESSFUL;
import static booksreview.audit.AuditLogsStatus.SUCCESSFUL;

@Service
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
    private final BooksRepository booksRepository;
    private final AuditLogsImpl auditLogsImplementation;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository, BooksRepository booksRepository, AuditLogsImpl auditLogsImplementation) {
        this.authorsRepository = authorsRepository;
        this.booksRepository = booksRepository;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    public List<AuthorsDto> getAuthorsList(int auditLogId) {
        List<AuthorsDto> authorsDtoList = new ArrayList<>();
        authorsRepository.findAll().forEach(author -> authorsDtoList.add(AuthorsMapper.modelMapper.map(author, AuthorsDto.class)));
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
        return authorsDtoList;
    }

    public List<BooksDto> getAuthorsListByAuthorID(int authorId, int auditLogId) {
        if(authorsRepository.existsById(authorId)) {
            Authors author = authorsRepository.findById(authorId).get();
            List<Books> booksFilteredListByAuthor = booksRepository.findAll().stream()
                    .filter(article -> article.getBookAuthor().equalsIgnoreCase(author.getName()))
                    .toList();
            List<BooksDto> booksDtoList = new ArrayList<>();
            booksFilteredListByAuthor
                    .forEach(book -> booksDtoList.add(BooksMapper
                            .modelMapper.map(book, BooksDto.class)));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return booksDtoList;
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new AuthorNotFoundException(authorId);
        }
    }

    public void addNewAuthor(String author) {
        if(authorsRepository.findByName(author) == null) {
            Authors newAuthor = new Authors();
            newAuthor.setName(author);
            authorsRepository.save(newAuthor);
            authorsRepoUpdate(author, "ADD");
        }
    }

    // ToCheck
    public void deleteAuthor(String author) {
        if (booksRepository.findByBookAuthor(author) == null) {
            authorsRepository.deleteById(authorsRepository.findByName(author).getId());
            authorsRepoUpdate(author, "DELETE");
        }
    }

    private void authorsRepoUpdate(String author, String operation) {
        String auditLogBody = operation + " author: " + author;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
    }
}
