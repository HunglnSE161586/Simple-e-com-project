package com.hung.shop.productImages.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductImageCreateRequest {
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
}
