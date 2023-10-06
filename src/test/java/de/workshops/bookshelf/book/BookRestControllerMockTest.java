package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockTest {

    @MockBean
    BookService serviceMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Captor
    ArgumentCaptor<String> isbnCaptor;
    @Test
    void shouldGetAllBooks() throws Exception {
        when(serviceMock.getAllBooks()).thenReturn(List.of());

        final var mvcResult = mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andReturn();

        final var payload = mvcResult.getResponse().getContentAsString();
        List<Book> books = mapper.readValue(payload, new TypeReference<>() {});

        assertThat(books).isEmpty();
    }
    @Test
    void getByIsbn() throws Exception {
        when(serviceMock.getBookByIsbn(isbnCaptor.capture())).thenReturn(null);

        mockMvc.perform(get("/book/1234567890"))
                .andExpect(status().isOk())
                .andReturn();

        final var value = isbnCaptor.getValue();
        assertThat(value).isEqualTo("1234567890");
    }

    @Test
    void shouldCreateBook() throws Exception {
        var isbn = "1234567890";
        var title = "My first book";
        var author = "Birgit";
        var description = "Description for My first book";

        var mvcResult = mockMvc.perform(post("/book")
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}