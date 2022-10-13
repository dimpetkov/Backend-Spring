package booksreview.repositories;

import booksreview.entities.Languages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguagesRepository extends JpaRepository<Languages, Integer> {
    Languages findByLanguage(String language);
}
