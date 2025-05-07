package com.hung.shop.productReview.service.impl;

import com.hung.shop.product.service.IProductExistenceChecker;
import com.hung.shop.product.service.IProductService;
import com.hung.shop.productReview.dto.request.ProductReviewCreateRequest;
import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.exception.ProductNotExistException;
import com.hung.shop.productReview.exception.UserNotExistException;
import com.hung.shop.productReview.mapper.ProductReviewMapper;
import com.hung.shop.productReview.repository.ProductReviewRepository;
import com.hung.shop.productReview.service.IProductReviewService;
import com.hung.shop.share.ProductReviewPOJO;
import com.hung.shop.user.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService implements IProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewMapper productReviewMapper;
    private final IProductExistenceChecker productExistenceChecker;
    private final IUserService userService;

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
        if (productExistenceChecker.existsById(productReviewCreateRequest.getProductId()) == false) {
            throw new ProductNotExistException("Product not found");
        }
        if (userService.getUserById(productReviewCreateRequest.getUserId()) == null) {
            throw new UserNotExistException("User not found");
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
