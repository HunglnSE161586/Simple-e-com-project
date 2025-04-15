package com.hung.shop.dto.respond;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String description;
    private Double price;
    private Boolean isActive;
    private Boolean isFeatured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
