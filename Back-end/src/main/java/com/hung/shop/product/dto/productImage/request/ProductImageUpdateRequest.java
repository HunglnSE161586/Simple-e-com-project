package com.hung.shop.product.dto.productImage.request;

import lombok.Data;

@Data
public class ProductImageUpdateRequest {
    private String imageUrl;
    private String altText;
}
