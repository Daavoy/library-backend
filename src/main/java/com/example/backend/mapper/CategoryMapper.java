package com.example.backend.mapper;

import com.example.backend.DTO.CategoryRequest;
import com.example.backend.DTO.CategoryResponse;
import com.example.backend.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setCategoryCode(category.getCategoryCode());
        categoryResponse.setAlternativeName(category.getAlternativeName());
        return categoryResponse;
    }

    public Category toEntity(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return null;
        }
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryCode(categoryRequest.getCategoryCode());
        category.setAlternativeName(categoryRequest.getAlternativeName());
        return category;
    }
}
