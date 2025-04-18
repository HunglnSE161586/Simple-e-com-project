package com.hung.shop.productImages.service.impl;

import com.hung.shop.productImages.dto.response.ProductImageDto;
import com.hung.shop.productImages.mapper.ProductImageMapper;
import com.hung.shop.productImages.repository.ProductImageRepository;
import com.hung.shop.productImages.service.IProductImageService;
import com.hung.shop.share.ProductImagePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductImageMapper productImageMapper;

    public List<ProductImageDto> getAllProductImages() {
        return productImageRepository.findAll().stream()
                .map(productImageMapper::toDto)
                .toList();
    }
    public List<ProductImageDto> getProductImagesByProductId(Long productId) {
        return productImageRepository.findAll().stream()
                .filter(productImage -> productImage.getProductId().equals(productId))
                .map(productImageMapper::toDto)
                .toList();
    }
    @Override
    public List<ProductImagePOJO> getProductImagesPojoByProductId(Long productId) {
        return productImageRepository.findAll().stream()
                .filter(productImage -> productImage.getProductId().equals(productId))
                .map(productImageMapper::toPOJO)
                .toList();
    }
}
