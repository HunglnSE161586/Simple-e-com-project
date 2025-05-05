package com.hung.shop.productImages.service;

import com.hung.shop.share.ProductImagePOJO;

import java.util.List;

public interface IProductImageQueryPort {
    List<ProductImagePOJO> getProductImagesPojoByProductId(Long productId);
}
