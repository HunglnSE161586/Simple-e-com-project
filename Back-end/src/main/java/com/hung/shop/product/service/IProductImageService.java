package com.hung.shop.product.service;

import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import com.hung.shop.product.dto.productImage.request.ProductImageUpdateRequest;
import com.hung.shop.product.dto.productImage.response.ProductImageDto;

import java.util.List;

public interface IProductImageService {
    List<ProductImageDto> getAllProductImages();
    List<ProductImageDto> getProductImagesByProductId(Long productId);

    void createProductImage(List<ProductImageCreateRequest> productImageCreateRequests, Long productId);
    ProductImageDto updateProductImage(Long id, ProductImageUpdateRequest productImageUpdateRequest);

}
