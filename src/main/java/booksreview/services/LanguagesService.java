package booksreview.services;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.LanguagesDto;
import booksreview.entities.Books;
import booksreview.entities.Languages;
import booksreview.exceptions.LanguageNotFoundException;
import booksreview.exceptions.RequestNotCorrect;
import booksreview.mappers.BooksMapper;
import booksreview.mappers.LanguagesMapper;
import booksreview.repositories.BooksRepository;
import booksreview.repositories.LanguagesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static booksreview.audit.AuditLogsStatus.NOT_SUCCESSFUL;
import static booksreview.audit.AuditLogsStatus.SUCCESSFUL;

@Service
public class LanguagesService {
    private final LanguagesRepository languagesRepository;
    private final BooksRepository booksRepository;
    private final AuditLogsImpl auditLogsImplementation;

    public LanguagesService(LanguagesRepository languagesRepository,
                            BooksRepository booksRepository, AuditLogsImpl auditLogsImplementation) {
        this.languagesRepository = languagesRepository;
        this.booksRepository = booksRepository;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    public List<LanguagesDto> getLanguagesList(int auditLogId) {
        List<LanguagesDto> languagesDtoList = new ArrayList<>();
        try {
            languagesRepository.findAll().forEach(language -> languagesDtoList.add(LanguagesMapper.modelMapper.map(language, LanguagesDto.class)));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return languagesDtoList;
        } catch (Exception er){
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new RequestNotCorrect();
        }
    }

    public List<BooksDto> getArticlesListByLanguageId(int languageId, int auditLogId) {
        if(languagesRepository.existsById(languageId)) {
            Languages language = languagesRepository.findById(languageId).get();
            List<Books> booksFilteredListByLanguage = booksRepository.findAll().stream()
                    .filter(article -> article.getBookLanguage().equalsIgnoreCase(language.getLanguage())).toList();
            List<BooksDto> booksDtoList = new ArrayList<>();
            booksFilteredListByLanguage.forEach(book -> booksDtoList.add(BooksMapper.modelMapper.map(book, BooksDto.class)));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return booksDtoList;
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new LanguageNotFoundException(languageId);
        }
    }

    public void addNewLanguage(String language) {
        if(languagesRepository.findByLanguage(language) == null) {
            Languages languageToAdd = new Languages();
            languageToAdd.setLanguage(language);
            languagesRepository.save(languageToAdd);
            languageRepoUpdate(language, "ADD");
        }
    }

    public void deleteLanguage(String language) {
        if(booksRepository.findByBookLanguage(language) == null) {
            languagesRepository.deleteById(languagesRepository.findByLanguage(language).getId());
            languageRepoUpdate(language, "DELETE");
        }
    }

    private void languageRepoUpdate(String language, String operation) {
        String auditLogBody = operation + " language: " + language;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
    }
}
