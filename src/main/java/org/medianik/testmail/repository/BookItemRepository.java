package org.medianik.testmail.repository;

import org.medianik.testmail.model.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface BookItemRepository extends JpaRepository<BookItem, Long>{

    Optional<BookItem> findBookItemByBook_Id(@Param("bookId") Long bookId);
}
