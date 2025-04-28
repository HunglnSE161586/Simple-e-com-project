package com.hung.shop.productReview.service.impl;

import com.hung.shop.product.service.IProductService;
import com.hung.shop.productReview.dto.request.ProductReviewCreateRequest;
import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.mapper.ProductReviewMapper;
import com.hung.shop.productReview.repository.ProductReviewRepository;
import com.hung.shop.productReview.service.IProductReviewService;
import com.hung.shop.share.ProductReviewPOJO;
import com.hung.shop.user.service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewService implements IProductReviewService {
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductReviewMapper productReviewMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;

    @Override
    public List<ProductReviewDto> getAllProductReviewsByProductId(Long productId) {
        return productReviewRepository.findAllByProductId(productId)
                .stream()
                .map(productReviewMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductReviewDto createProductReview(ProductReviewCreateRequest productReviewCreateRequest) {
        if (productService.findById(productReviewCreateRequest.getProductId()).isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        if (userService.getUserById(productReviewCreateRequest.getUserId()) == null) {
            throw new IllegalArgumentException("User not found");
        }
        return productReviewMapper.toDto(
                productReviewRepository.save(
                        productReviewMapper.toEntity(productReviewCreateRequest)
                )
        );
    }

    @Override
    public List<ProductReviewPOJO> getProductReviewsPojoByProductId(Long productId) {
        return productReviewRepository.findAllByProductId(productId)
                .stream()
                .map(productReviewMapper::toPOJO)
                .toList();
    }
}
