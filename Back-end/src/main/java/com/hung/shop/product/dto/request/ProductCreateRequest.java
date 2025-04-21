package com.hung.shop.product.dto.request;

import lombok.Data;

@Data
public class ProductCreateRequest {
    private String productName;
    private String description;
    private Double price;
    private Long categoryId;
    private Long stock;
}
