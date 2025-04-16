package com.hung.shop.services;

import com.hung.shop.dto.respond.ProductImageDto;
import com.hung.shop.mapper.ProductImageMapper;
import com.hung.shop.repositories.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
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
                .filter(productImage -> productImage.getProduct().getProductId().equals(productId))
                .map(productImageMapper::toDto)
                .toList();
    }
}
