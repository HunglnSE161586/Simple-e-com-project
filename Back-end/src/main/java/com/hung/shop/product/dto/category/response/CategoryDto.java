package com.hung.shop.product.dto.category.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String description;
    private LocalDateTime createdAt;
    private String image;
}
