package com.example.backend.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private String description;
    private String isbn;
    @Column(name = "is_favorite")
    private boolean isFavourite;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationYear;
    private Integer totalPages;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonManagedReference
    private Set<Category> categories;

    @Column(name = "thumbnail")
    private byte[] thumbnail;

    public Book(String title, String author, String description, String isbn, LocalDate publicationYear) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }
    public Book(String title, String author, Integer totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
    }
    public Book(){}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

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
}