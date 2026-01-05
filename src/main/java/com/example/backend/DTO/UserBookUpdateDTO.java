package com.example.backend.DTO;

import com.example.backend.models.ReadingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserBookUpdateDTO {
    private ReadingStatus readingStatus;
    private Integer currentPage;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private String notes;
    private Boolean isFavorite;
}
