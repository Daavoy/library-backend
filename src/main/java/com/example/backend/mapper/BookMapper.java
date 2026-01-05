package com.example.backend.mapper;

import com.example.backend.DTO.BookDTO;
import com.example.backend.DTO.BookUpdateDTO;
import com.example.backend.models.Book;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class BookMapper {

    public BookDTO toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());

        if (book.getThumbnail() != null) {
            dto.setThumbnailBase64(
                    Base64.getEncoder().encodeToString(book.getThumbnail())
            );
        }

        return dto;
    }

    public Book toEntity(BookUpdateDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Book(
                dto.getTitle(),
                dto.getAuthor(),
                dto.getDescription(),
                dto.getIsbn(),
                dto.getPublicationYear()
        );
    }

    public void updateEntity(BookUpdateDTO dto, Book entity) {
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setDescription(dto.getDescription());
        entity.setIsbn(dto.getIsbn());
        entity.setPublicationYear(dto.getPublicationYear());
    }
}