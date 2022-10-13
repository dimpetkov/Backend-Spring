package booksreview.repositories;

import booksreview.entities.Categories;
import booksreview.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    Categories findByCategory(String category);
}
