package com.example.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class BookRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    private String description;

    @Pattern(regexp = "\\d{13}", message = "ISBN must be 13 digits")
    private String isbn;

    private LocalDate publicationYear;

    @Positive(message = "Total pages must be a positive number")
    private Integer totalPages;

    // MultipartFile because books are created via multipart/form-data,
    // not JSON — the thumbnail comes as a file upload not a string
    private MultipartFile thumbnail;
}
