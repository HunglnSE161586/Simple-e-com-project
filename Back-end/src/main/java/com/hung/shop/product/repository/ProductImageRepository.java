package com.hung.shop.product.repository;

import com.hung.shop.product.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
    @Query("""
    SELECT pi FROM ProductImages pi
    WHERE pi.productId IN :productIds
      AND pi.displayOrder = (
          SELECT MIN(pii.displayOrder)
          FROM ProductImages pii
          WHERE pii.productId = pi.productId
      )
    """)
    List<ProductImages> findMainImagesForProductIds(@Param("productIds") List<Long> productIds);

}
