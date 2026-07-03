package com.example.backend.DTO;

import com.example.backend.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String categoryCode;
    private String alternativeName;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.categoryCode = category.getCategoryCode();
        this.alternativeName = category.getAlternativeName();
    }
    public CategoryResponse() {}
}
