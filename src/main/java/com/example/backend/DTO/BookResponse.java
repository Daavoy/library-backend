package com.example.backend.DTO;

import com.example.backend.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private LocalDate publicationYear;
    private Integer totalPages;
    private boolean hasThumbnail;
    private Set<CategoryResponse> categories;

    public BookResponse() {}

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.isbn = book.getIsbn();
        this.publicationYear = book.getPublicationYear();
        this.totalPages = book.getTotalPages();
        this.hasThumbnail = book.getThumbnail() != null;
        this.categories = book.getCategories() == null ? Set.of() :
                book.getCategories().stream()
                        .map(CategoryResponse::new)
                        .collect(Collectors.toSet());
    }
}