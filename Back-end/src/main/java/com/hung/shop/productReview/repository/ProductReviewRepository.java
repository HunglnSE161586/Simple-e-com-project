package com.hung.shop.productReview.repository;

import com.hung.shop.productReview.entity.ProductReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviews, Long> {
    Optional<ProductReviews> findAllByProductId(Long productId);
}
