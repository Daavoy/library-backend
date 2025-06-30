package com.example.backend.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationYear;
    private String thumbnailBase64;
    public BookDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getThumbnailBase64() {
        return thumbnailBase64;
    }

    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public LocalDate getPublicationYear() { return publicationYear; }
    public void setPublicationYear(LocalDate publicationYear) { this.publicationYear = publicationYear; }
}
