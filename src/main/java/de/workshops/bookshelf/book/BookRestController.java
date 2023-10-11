package de.workshops.bookshelf.book;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable @Size(min = 10, max = 14) String isbn) {
        final var foundBook = service.getBookByIsbn(isbn);
        return ResponseEntity.ok(foundBook);
    }

    @GetMapping(params = "authorName")
    public Book getBookByAuthor(@RequestParam(name = "authorName") @NotBlank @Size(min = 3) String author) {
        return service.getBookByAuthor(author);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> search(@RequestBody @Valid BookSearchRequest searchRequest) {
        final var foundBooks = service.searchForBooks(searchRequest);

        if (foundBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundBooks);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        service.createBook(book);
        return ResponseEntity.ok(book);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> validationError(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
