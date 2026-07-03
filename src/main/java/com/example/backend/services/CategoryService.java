package com.example.backend.services;

import com.example.backend.DTO.CategoryRequest;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.models.Category;
import com.example.backend.repositories.CategoryRepository;
import exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    public Category addCategory(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.delete(category);
    }
}
