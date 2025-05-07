package com.hung.shop.share;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductPojo {
    private Long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private Boolean isActive;
    private Boolean isFeatured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long stock;
    private Long categoryId;
    private ProductImagePOJO productImagePOJO;
}
