package com.hung.shop.productReview.mapper;

import com.hung.shop.productReview.dto.request.ProductReviewCreateRequest;
import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.entity.ProductReviews;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    ProductReviewDto toDto(ProductReviews productReview);
    ProductReviews toEntity(ProductReviewCreateRequest productReviewCreateRequest);
}
