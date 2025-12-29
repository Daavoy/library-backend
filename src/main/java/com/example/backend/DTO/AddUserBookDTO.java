package com.example.backend.DTO;

import com.example.backend.models.ReadingStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserBookDTO {
    private Long bookId;
    private String title;
    private String author;
    private Integer totalPages;
    private Integer currentPage;
    private ReadingStatus readingStatus;

}
