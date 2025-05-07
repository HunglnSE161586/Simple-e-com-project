package com.hung.shop.share;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductReviewPOJO {
    private Long id;
    private Long productId;
    private Long userId;
    private int rating;
    private String reviewText;
    private LocalDateTime createdAt;
}
