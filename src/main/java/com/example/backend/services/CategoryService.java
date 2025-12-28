package com.example.backend.services;

import com.example.backend.models.Category;
import com.example.backend.repositories.CategoryRepository;

import exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {this.categoryRepository = categoryRepository;}

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }
    public void deleteCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        categoryRepository.delete(category);
    }

}
