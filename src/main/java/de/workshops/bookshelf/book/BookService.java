package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
    private final BookJpaRepository repository;

    BookService(BookJpaRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.findAll();
    }

    Book getBookByIsbn (String isbn) {
        return repository.findByIsbn(isbn);
    }

    Book getBookByAuthor(String author) {
        return repository.findAll().stream()
                .filter(book -> hasAuthor(book, author))
                .findAny()
                .orElseThrow();
    }

    List<Book> searchForBooks(BookSearchRequest searchRequest) {
        return repository.findByIsbnOrAuthorContaining(searchRequest.isbn(), searchRequest.authorName());
    }

    void createBook (Book book) {
        repository.save(book);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

}
