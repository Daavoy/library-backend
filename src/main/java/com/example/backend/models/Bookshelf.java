package com.example.backend.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Bookshelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "bookshelf")
    private List<Book> books;
    public Bookshelf() {}

    public Bookshelf(List<Book> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
