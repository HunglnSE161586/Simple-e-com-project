package com.hung.shop.product.dto.product.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String productName;
    private String description;
    private Double price;
    private Long categoryId;
    private Boolean isFeatured;
    private Long stock;
}
