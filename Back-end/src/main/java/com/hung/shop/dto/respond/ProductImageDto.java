package com.hung.shop.dto.respond;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductImageDto {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
    private LocalDateTime createdAt;
}
