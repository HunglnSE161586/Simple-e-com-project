package com.hung.shop.product.dto.productImage.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductImageDto {
    private Long id;
    private Long productId;
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
    private LocalDateTime createdAt;
}
