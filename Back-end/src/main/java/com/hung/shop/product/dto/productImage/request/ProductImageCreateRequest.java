package com.hung.shop.product.dto.productImage.request;

import lombok.Data;

@Data
public class ProductImageCreateRequest {
    private String imageUrl;
    private String altText;
    private Integer displayOrder;
}
