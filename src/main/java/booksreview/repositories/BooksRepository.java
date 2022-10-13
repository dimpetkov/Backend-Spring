package booksreview.repositories;

import booksreview.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, Integer> {
    Books findByBookLanguage(String bookLanguage);
    Books findByBookAuthor(String bookAuthor);
    Books findByBookCategory(String bookCategory);
    Books findBookByBookName(String bookName);
}
