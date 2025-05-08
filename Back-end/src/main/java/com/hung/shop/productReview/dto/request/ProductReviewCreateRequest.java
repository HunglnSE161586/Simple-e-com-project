package com.hung.shop.productReview.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProductReviewCreateRequest {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @Length(max = 254, message = "Review text cannot exceed 254 characters")
    private String reviewText;
    @NotNull(message = "Rating cannot be null")
    private int rating;
}
