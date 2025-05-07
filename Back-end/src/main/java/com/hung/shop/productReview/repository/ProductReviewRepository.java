package com.hung.shop.productReview.repository;

import com.hung.shop.productReview.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findAllByProductId(Long productId);
}
