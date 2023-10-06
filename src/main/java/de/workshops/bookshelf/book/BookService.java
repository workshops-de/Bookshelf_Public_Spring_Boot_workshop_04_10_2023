package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
    private final BookRepository repository;

    BookService(BookRepository repository) {
        this.repository = repository;
    }

    List<Book> getAllBooks() {
        return repository.findAllBooks();
    }

    Book getBookByIsbn (String isbn) {
        return repository.findAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .findAny()
                .orElseThrow();
    }

    Book getBookByAuthor(String author) {
        return repository.findAllBooks().stream()
                .filter(book -> hasAuthor(book, author))
                .findAny()
                .orElseThrow();
    }


    List<Book> searchForBooks(BookSearchRequest searchRequest) {
        return repository.findAllBooks().stream()
                .filter(book -> hasIsbn(book, searchRequest.isbn()) || hasAuthor(book, searchRequest.authorName()))
                .toList();
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

}
