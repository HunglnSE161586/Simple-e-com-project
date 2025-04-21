package com.hung.shop.productReview.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductReviewDto {
    private Long reviewId;
    private Long productId;
    private Long userId;
    private int rating;
    private String reviewText;
    private LocalDateTime createdAt;
}
