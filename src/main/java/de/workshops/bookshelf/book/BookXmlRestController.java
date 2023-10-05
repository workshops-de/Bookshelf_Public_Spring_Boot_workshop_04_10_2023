package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/xml/book")
public class BookXmlRestController {

    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    private List<Book> books;

    public BookXmlRestController(ObjectMapper mapper, ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<Book> getAllBooks() {
        return books;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public List<Book> search(@RequestBody @Valid BookSearchRequest searchRequest) {
        return books.stream()
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
