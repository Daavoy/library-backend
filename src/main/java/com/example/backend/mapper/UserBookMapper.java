package com.example.backend.mapper;

import com.example.backend.DTO.UserBookDTO;
import com.example.backend.models.UserBook;
import org.springframework.stereotype.Component;

@Component
public class UserBookMapper {

    public UserBookDTO toDto(UserBook entity) {
        UserBookDTO dto = new UserBookDTO();
        dto.setBookId(entity.getBook().getId());
        dto.setTitle(entity.getBook().getTitle());
        dto.setAuthor(entity.getBook().getAuthor());
        dto.setStatus(entity.getStatus());
        dto.setCurrentPage(entity.getCurrentPage());
        dto.setStartedAt(entity.getStartedAt());
        dto.setFinishedAt(entity.getFinishedAt());
        dto.setId(entity.getId());
        return dto;
    }
    public UserBook toEntity(UserBookDTO entity) {
        //TODO: create this.
        return null;
    }
}
