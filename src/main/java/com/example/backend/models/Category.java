package com.example.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String categoryName;
    private String categoryCode;
    private String alternativeName;
    public Category() {}
    public Category(String categoryName, String categoryCode, String alternativeName) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.alternativeName = alternativeName;
    }
    @ManyToMany(mappedBy = "categories")
    private Set<Book> books;

}

