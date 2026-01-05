package com.example.backend.DTO;

import com.example.backend.models.ReadingStatus;
import com.example.backend.models.UserBook;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserBookDTO {
    private Boolean isFavorite;
    private Long id;
    private Long bookId;
    private String title;
    private String author;
    private ReadingStatus status;
    private Integer currentPage;
    private LocalDate startedAt;
    private LocalDate finishedAt;
    private String notes;
    public UserBookDTO(UserBook userBook) {
        this.id = userBook.getId();
        this.currentPage = userBook.getCurrentPage();
        this.isFavorite = userBook.getIsFavorite();
        this.notes = userBook.getNotes();
    }
    public UserBookDTO(){}
}