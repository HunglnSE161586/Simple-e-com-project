package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductPojo {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Boolean isActive;
    private Boolean isFeatured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long stock;
    private Long categoryId;
    private ProductImagePOJO productImagePOJO;
}
