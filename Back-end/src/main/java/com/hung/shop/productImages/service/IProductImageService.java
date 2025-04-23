package com.hung.shop.productImages.service;

import com.hung.shop.productImages.dto.request.ProductImageCreateRequest;
import com.hung.shop.productImages.dto.request.ProductImageUpdateRequest;
import com.hung.shop.productImages.dto.response.ProductImageDto;
import com.hung.shop.share.ProductImagePOJO;
import com.hung.shop.share.ProductPojo;

import java.util.List;
import java.util.Map;

public interface IProductImageService {
    List<ProductImageDto> getAllProductImages();
    List<ProductImageDto> getProductImagesByProductId(Long productId);
    List<ProductImagePOJO> getProductImagesPojoByProductId(Long productId);
    void createProductImage(List<ProductImageCreateRequest> productImageCreateRequests, Long productId);
    ProductImageDto updateProductImage(Long id, ProductImageUpdateRequest productImageUpdateRequest);
    Map<Long,ProductImagePOJO> getMainProductImagesByProductId(List<Long> productIds);
}
