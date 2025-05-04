package com.hung.shop.product.dto.category.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String categoryName;
    private String description;
    private String image;
}
