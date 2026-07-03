package com.example.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message="Name is required")
    private String categoryName;
    @NotBlank(message="Code is required")
    private String categoryCode;

    private String alternativeName;
}
