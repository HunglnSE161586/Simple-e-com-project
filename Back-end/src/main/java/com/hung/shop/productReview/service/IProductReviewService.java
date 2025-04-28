package com.hung.shop.productReview.service;

import com.hung.shop.productReview.dto.request.ProductReviewCreateRequest;
import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.share.ProductReviewPOJO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductReviewService {
    List<ProductReviewDto> getAllProductReviewsByProductId(Long productId);
    ProductReviewDto createProductReview(ProductReviewCreateRequest productReviewCreateRequest);
    List<ProductReviewPOJO> getProductReviewsPojoByProductId(Long productId);
}
