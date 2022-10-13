package booksreview.repositories;

import booksreview.entities.Authors;
import booksreview.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository<Authors, Integer> {
    Authors findByName(String author);
}
