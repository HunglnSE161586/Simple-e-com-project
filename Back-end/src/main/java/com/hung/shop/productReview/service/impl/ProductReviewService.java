package com.hung.shop.productReview.service.impl;

import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.mapper.ProductReviewMapper;
import com.hung.shop.productReview.repository.ProductReviewRepository;
import com.hung.shop.productReview.service.IProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewService implements IProductReviewService {
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductReviewMapper productReviewMapper;

    @Override
    public List<ProductReviewDto> getAllProductReviewsByProductId(Long productId) {
        return productReviewRepository.findAllByProductId(productId)
                .stream()
                .map(productReviewMapper::toDto)
                .toList();
    }
}
