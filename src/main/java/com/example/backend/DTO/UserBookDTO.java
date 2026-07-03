package com.example.backend.DTO;

import com.example.backend.models.ReadingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserBookDTO {
    private Boolean isFavorite;
    private Long id;
    private BookResponse book;
    private ReadingStatus status;
    private Long userId;
    private Integer currentPage;
    private LocalDate startedAt;
    private LocalDate finishedAt;
    private String notes;
    private Double rating;

    public UserBookDTO(){}
}