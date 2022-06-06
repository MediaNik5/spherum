package org.medianik.testmail.repository;

import org.medianik.testmail.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
