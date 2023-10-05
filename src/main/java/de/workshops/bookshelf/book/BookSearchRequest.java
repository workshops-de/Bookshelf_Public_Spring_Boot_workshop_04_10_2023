package de.workshops.bookshelf.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookSearchRequest(@Size(min = 3) @NotBlank String authorName, @Size(min = 10, max = 14) String isbn) { }
