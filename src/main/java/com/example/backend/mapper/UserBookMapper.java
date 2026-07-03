package com.example.backend.mapper;

import com.example.backend.DTO.UserBookDTO;
import com.example.backend.models.UserBook;
import org.springframework.stereotype.Component;

@Component
public class UserBookMapper {

    private final BookMapper bookMapper;

    public UserBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public UserBookDTO toDto(UserBook entity) {
        UserBookDTO dto = new UserBookDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setBook(bookMapper.toResponse(entity.getBook()));
        dto.setStatus(entity.getStatus());
        dto.setCurrentPage(entity.getCurrentPage());
        dto.setStartedAt(entity.getStartedAt());
        dto.setFinishedAt(entity.getFinishedAt());
        dto.setNotes(entity.getNotes());
        dto.setRating(entity.getRating());
        dto.setIsFavorite(entity.getIsFavorite());
        return dto;
    }


    public UserBook toEntity(UserBookDTO dto) {
        throw new UnsupportedOperationException(
                "UserBook creation requires repository lookups — use UserBookService.saveUserBook() instead"
        );
    }
}