package com.hung.shop.categories.dto.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String categoryName;
    private String description;
    private String image;
}
