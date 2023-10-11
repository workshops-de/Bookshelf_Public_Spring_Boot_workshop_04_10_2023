package de.workshops.bookshelf.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookJpaRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);

    List<Book> findByIsbnOrAuthorContaining(String isbn, String author);

    @Query(name = "getBookTitle")
    String getBookTitle(String isbn);
}
