package com.example.backend.controllers;
import com.example.backend.DTO.CategoryRequest;
import com.example.backend.DTO.CategoryResponse;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.models.Category;
import com.example.backend.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories.stream().map(categoryMapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable long id) {
        return categoryService.findById(id)
                .map(CategoryResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody  CategoryRequest request) {
        Category category = categoryService.addCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryResponse(category));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestParam long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

}
