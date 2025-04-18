package com.hung.shop.productImages.service;

import com.hung.shop.productImages.dto.response.ProductImageDto;
import com.hung.shop.share.ProductImagePOJO;

import java.util.List;

public interface IProductImageService {
    List<ProductImageDto> getAllProductImages();
    List<ProductImageDto> getProductImagesByProductId(Long productId);
    List<ProductImagePOJO> getProductImagesPojoByProductId(Long productId);
}
