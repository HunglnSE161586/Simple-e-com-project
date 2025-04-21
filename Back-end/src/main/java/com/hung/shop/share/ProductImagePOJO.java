package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProductImagePOJO {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
    private LocalDateTime createdAt;
}
