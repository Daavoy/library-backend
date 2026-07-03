package com.example.backend.mapper;


import com.example.backend.DTO.BookRequest;
import com.example.backend.DTO.BookResponse;
import com.example.backend.DTO.CategoryResponse;
import com.example.backend.models.Book;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setDescription(book.getDescription());
        response.setIsbn(book.getIsbn());
        response.setPublicationYear(book.getPublicationYear());
        response.setTotalPages(book.getTotalPages());
        response.setHasThumbnail(book.getThumbnail() != null);
        response.setCategories(
                book.getCategories() == null ? Set.of() :
                        book.getCategories().stream()
                                .map(CategoryResponse::new)
                                .collect(Collectors.toSet())
        );


        return response;
    }

    public Book toEntity(BookRequest request) {
        if (request == null) return null;

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setTotalPages(request.getTotalPages());

        return book;
    }
    public void updateEntity(BookRequest request, Book book) {
        if (request == null) return;

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setTotalPages(request.getTotalPages());
        // Thumbnail is only updated if the caller explicitly passes one
        // That logic stays in the service, not here
    }
}