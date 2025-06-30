package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationYear;

    @Column(name = "thumbnail")
    private byte[] thumbnail;

    public Book(String title, String author, String description, String isbn, LocalDate publicationYear) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public Book(){}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", thumbnail=" + Arrays.toString(thumbnail) +
                '}';
    }

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getThumbnail() { return thumbnail; }

    public LocalDate getPublicationYear() { return publicationYear; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(byte[] thumbnail) { this.thumbnail = thumbnail; }
    public void setPublicationYear(LocalDate publicationYear) { this.publicationYear = publicationYear; }
}