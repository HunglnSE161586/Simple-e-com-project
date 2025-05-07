package com.hung.shop.product.dto.product.request;

import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCreateRequest {
    @NotNull(message = "Product name cannot be null")
    private String productName;
    private String description;
    @NotNull(message = "Price cannot be null")
    private BigDecimal price;
    @NotNull(message = "Category id cannot be null")
    private Long categoryId;
    private Long stock;
    private List<ProductImageCreateRequest> productImages;
}
