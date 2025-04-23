package com.hung.shop.productImages.dto.request;

import lombok.Data;

@Data
public class ProductImageUpdateRequest {
    private String imageUrl;
    private String altText;
}
