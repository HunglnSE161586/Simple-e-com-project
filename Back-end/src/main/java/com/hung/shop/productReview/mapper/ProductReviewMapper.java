package com.hung.shop.productReview.mapper;

import com.hung.shop.productReview.dto.request.ProductReviewCreateRequest;
import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.entity.ProductReview;
import com.hung.shop.share.ProductReviewPOJO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    ProductReviewDto toDto(ProductReview productReview);
    ProductReview toEntity(ProductReviewCreateRequest productReviewCreateRequest);
    ProductReviewPOJO toPOJO(ProductReview productReview);
}
