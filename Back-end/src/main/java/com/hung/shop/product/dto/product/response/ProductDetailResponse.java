package com.hung.shop.product.dto.product.response;

import com.hung.shop.share.CategoryPOJO;
import com.hung.shop.share.ProductImagePOJO;
import com.hung.shop.share.ProductReviewPOJO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDetailResponse {
    private Long productId;
    private String productName;
    private String description;
    private Double price;
    private Boolean isActive;
    private Boolean isFeatured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long stock;
    private List<ProductImagePOJO> productImagePOJOS;
    private CategoryPOJO categoryPOJO;
    private List<ProductReviewPOJO> productReviewPOJOS;
}
