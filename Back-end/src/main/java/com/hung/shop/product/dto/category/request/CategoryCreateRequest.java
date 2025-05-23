package com.hung.shop.product.dto.category.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryCreateRequest {
    @NotNull(message = "Category name cannot be null")
    private String categoryName;
    private String description;
    private String image;
}
