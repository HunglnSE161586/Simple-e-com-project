package com.hung.shop.product.dto.product.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Boolean isFeatured;
    private Long stock;
}
