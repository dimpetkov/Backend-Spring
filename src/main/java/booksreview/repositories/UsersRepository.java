package booksreview.repositories;

import booksreview.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findUserByName(String name);

}
