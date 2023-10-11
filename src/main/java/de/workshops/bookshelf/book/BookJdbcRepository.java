package de.workshops.bookshelf.book;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
class BookJdbcRepository {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;


    BookJdbcRepository(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
        this.template = template;
        this.namedTemplate = namedTemplate;
    }

    List<Book> findAllBooks() {
        var sql = "select * from book";

        return template.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
    }

    void saveBook(Book newBook) {
        String sql = "INSERT INTO book (title, description, author, isbn) VALUES (?, ?, ?, ?)";
        template.update(sql, newBook.getTitle(), newBook.getDescription(), newBook.getAuthor(), newBook.getIsbn());
    }
    void saveBookNamed(Book newBook) {
        String sql = "INSERT INTO book (title, description, author, isbn) VALUES (:title, :description, :author, :isbn)";
        var params = Map.of("title", newBook.getTitle(),
                "description", newBook.getDescription(),
                "author", newBook.getAuthor(),
                "isbn", newBook.getIsbn()
        );
        namedTemplate.update(sql, params);
    }
}
