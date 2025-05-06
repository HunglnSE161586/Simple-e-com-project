package com.hung.shop.product.service;

import com.hung.shop.share.ProductImagePOJO;

import java.util.List;
import java.util.Map;

public interface IProductMainImageService {
    Map<Long, ProductImagePOJO> getMainProductImagesByProductId(List<Long> productIds);
}
