package booksreview.services;

import booksreview.audit.AuditLogsImpl;
import booksreview.dto.BooksDto;
import booksreview.dto.CategoriesDto;
import booksreview.entities.Books;
import booksreview.entities.Categories;
import booksreview.exceptions.CategoryNotFoundException;
import booksreview.mappers.BooksMapper;
import booksreview.mappers.CategoriesMapper;
import booksreview.repositories.BooksRepository;
import booksreview.repositories.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static booksreview.audit.AuditLogsStatus.NOT_SUCCESSFUL;
import static booksreview.audit.AuditLogsStatus.SUCCESSFUL;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final BooksRepository booksRepository;
    private final AuditLogsImpl auditLogsImplementation;

    public CategoriesService(CategoriesRepository categoriesRepository, BooksRepository booksRepository, AuditLogsImpl auditLogsImplementation) {
        this.categoriesRepository = categoriesRepository;
        this.booksRepository = booksRepository;
        this.auditLogsImplementation = auditLogsImplementation;
    }

    public List<CategoriesDto> getCategoriesList(int auditLogId) {
        List<CategoriesDto> categoriesDtoList = new ArrayList<>();
        categoriesRepository.findAll().forEach(category -> categoriesDtoList.add(CategoriesMapper.modelMapper.map(category, CategoriesDto.class)));
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
        return categoriesDtoList;
    }

    public List<BooksDto> getArticlesListByCategoryId(int categoryId, int auditLogId) {
        if(categoriesRepository.existsById(categoryId)) {
            Categories category = categoriesRepository.findById(categoryId).get();
            List<Books> booksFilteredListByCategory = booksRepository.findAll().stream()
                    .filter(article -> article.getBookCategory().equalsIgnoreCase(category.getCategory()))
                    .toList();
            List<BooksDto> booksDtoList = new ArrayList<>();
            booksFilteredListByCategory.forEach(book -> booksDtoList.add(BooksMapper.modelMapper.map(book, BooksDto.class)));
            auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
            return booksDtoList;
        } else {
            auditLogsImplementation.updateAuditLogStatus(auditLogId, NOT_SUCCESSFUL);
            throw new CategoryNotFoundException(categoryId);
        }
    }

    public void addNewCategory(String category) {
        if(categoriesRepository.findByCategory(category) == null) {
            Categories newCategory = new Categories();
            newCategory.setCategory(category);
            categoriesRepository.save(newCategory);
            categoriesRepoUpdate(category, "ADD");
        }
    }

    // ToCheck
    public void deleteCategory(String category) {
        if(booksRepository.findByBookCategory(category) == null) {
        categoriesRepository.deleteById(categoriesRepository.findByCategory(category).getId());
        categoriesRepoUpdate(category, "DELETE");
        }
    }

    private void categoriesRepoUpdate(String category, String operation) {
        String auditLogBody = operation + " category: " + category;
        int auditLogId = auditLogsImplementation.createAuditLog(auditLogBody);
        auditLogsImplementation.updateAuditLogStatus(auditLogId, SUCCESSFUL);
    }
}
